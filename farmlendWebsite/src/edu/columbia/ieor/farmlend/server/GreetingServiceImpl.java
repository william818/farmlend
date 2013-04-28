package edu.columbia.ieor.farmlend.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.columbia.ieor.farmlend.client.GreetingService;
import edu.columbia.ieor.farmlend.shared.EMF;
import edu.columbia.ieor.farmlend.shared.FieldVerifier;
import edu.columbia.ieor.farmlend.shared.model.Answer;
import edu.columbia.ieor.farmlend.shared.model.AnswerChoice;
import edu.columbia.ieor.farmlend.shared.model.AnswerChoiceModel;
import edu.columbia.ieor.farmlend.shared.model.AnswerModel;
import edu.columbia.ieor.farmlend.shared.model.Choice;
import edu.columbia.ieor.farmlend.shared.model.Country;
import edu.columbia.ieor.farmlend.shared.model.Crop;
import edu.columbia.ieor.farmlend.shared.model.CropAllModel;
import edu.columbia.ieor.farmlend.shared.model.CropPeriod;
import edu.columbia.ieor.farmlend.shared.model.CropPeriodModel;
import edu.columbia.ieor.farmlend.shared.model.Question;
import edu.columbia.ieor.farmlend.shared.model.QuestionModel;
import edu.columbia.ieor.farmlend.shared.model.RegressionCoefficientModel;
import edu.columbia.ieor.farmlend.shared.model.RegressionResultModel;
import edu.columbia.ieor.farmlend.shared.model.Section;
import edu.columbia.ieor.farmlend.shared.model.SectionModel;
import edu.columbia.ieor.farmlend.shared.model.Zone;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
        GreetingService {

    public String greetServer(String input) throws IllegalArgumentException {
        // Verify that the input is valid. 
        if (!FieldVerifier.isValidName(input)) {
            // If the input is not valid, throw an IllegalArgumentException back to
            // the client.
            throw new IllegalArgumentException(
                    "Name must be at least 4 characters long");
        }

        String serverInfo = getServletContext().getServerInfo();
        String userAgent = getThreadLocalRequest().getHeader("User-Agent");

        // Escape data from the client to avoid cross-site script vulnerabilities.
        input = escapeHtml(input);
        userAgent = escapeHtml(userAgent);

        runRegression();
        
        return "Hello, " + input + "!<br><br>I am running " + serverInfo
                + ".<br><br>It looks like you are using:<br>" + userAgent;
    }

    /**
     * Escape an html string. Escaping data received from the client helps to
     * prevent cross-site script vulnerabilities.
     * 
     * @param html the html string to escape
     * @return the escaped string
     */
    private String escapeHtml(String html) {
        if (html == null) {
            return null;
        }
        return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

    @Override
    public void addSection(String sectionName) {
        SectionModel sm = new SectionModel();
        sm.setName(sectionName);
        EntityManager em = EMF.get();
        EMF.save(em, sm);
        em.close();
    }
    
    @Override
    public void addQuestion(QuestionModel question) {
        EntityManager em = EMF.get();
        SectionModel s = EMF.findById(em, SectionModel.class, question.getSection().getId());
        question.setSection(s);
        s.getQuestions().add(question);
        EMF.save(em, s);
        em.close();
    }

    @Override
    public List<Section> getAllSections() {
        return getAllSections(false);
    }
    
    @Override
    public List<Section> getAllSectionsDeeply() {
        return getAllSections(true);
    }

    private List<Section> getAllSections(boolean deepFetch) {
        EntityManager em = EMF.get();
        Collection<SectionModel> sections = EMF.loadAll(em, "SectionModel", SectionModel.class);
        List<Section> newSections = new ArrayList<Section>();
        for (SectionModel sm : sections) {
            newSections.add(new Section(sm, deepFetch));
        }
        em.close();
        return newSections;
    }

    @Deprecated
    List<Section> getAllSectionsDeeplyOld() {
        return null;
        /*EntityManager entityManager = EMF.get();
        Query query = entityManager.createQuery("SELECT section FROM SectionModel section " + 
        "LEFT JOIN FETCH section.questions question "+
        "LEFT JOIN FETCH question.choices");
        Collection<SectionModel> sections = query.getResultList();
        List<Section> newSections = new ArrayList<Section>();
        for (SectionModel sm : sections) {
            newSections.add(new Section(sm));
        }
        return newSections;*/
    }

    @Override
    public void saveAnswer(Answer answer) {
        EntityManager em = EMF.get();
        
        AnswerModel am = new AnswerModel();
        am.setDescription(getThreadLocalRequest().getLocalAddr());
        am.setResult(answer.getResult());
        am.setSubmitted(answer.getSubmitted());
        
        List<AnswerChoiceModel> answerChoiceModels = new ArrayList<AnswerChoiceModel>();
        for(AnswerChoice ac : answer.getAnswerChoices()) {
            AnswerChoiceModel acm = new AnswerChoiceModel();
            acm.setAnswer(am);
            acm.setQuestionModelId(ac.getQuestionModelId());
            acm.setChoiceModelId(ac.getChoiceModelId());
            answerChoiceModels.add(acm);
        }
        
        am.setAnswerChoices(answerChoiceModels);

        EMF.save(em, am);
        em.close();
    }
    
    private Map<AnswerModel, Map<Long, Double>> getAnswers(EntityManager em, List<Section> sections) {
        Map<Long, Map<Long, Double>> valueMap = buildValueLookupMap(sections);
        HashMap<AnswerModel, Map<Long, Double>> allAnswers = new HashMap<AnswerModel, Map<Long, Double>>();
        

        Collection<AnswerModel> answerModels = EMF.loadAll(em, "AnswerModel", AnswerModel.class);
        for (AnswerModel am : answerModels) {
            allAnswers.put(am, getAnswerValues(am, valueMap));
        }
        
        return allAnswers;
    }
    
    private Map<Long, Double> getAnswerValues(AnswerModel am, Map<Long, Map<Long, Double>> valueMap) {
        
        Map<Long, Double> returnMap = new HashMap<Long, Double>();
        
        for (AnswerChoiceModel acm : am.getAnswerChoices()) {
            returnMap.put(acm.getQuestionModelId(), valueMap.get(acm.getQuestionModelId()).get(acm.getChoiceModelId()));
        }
        
        return returnMap;
    }
    
    private Map<Long, Map<Long, Double>> buildValueLookupMap(List<Section> sections) {
        Map<Long, Map<Long, Double>> returnMap = new HashMap<Long, Map<Long, Double>>();
        for (Section s : sections) {
            for (Question q : s.getQuestions()) {
                Map<Long, Double> choiceValueMap = new HashMap<Long, Double>();
                for (Choice c : q.getChoices()) {
                    choiceValueMap.put(c.getId(), c.getValue());
                }
                returnMap.put(q.getId(), choiceValueMap);
            }
        }
        
        return returnMap;
    }

    @Override
    public void runRegression() {
        List<Section> sections = getAllSections(true);
        
        List<Long> questionIds = new ArrayList<Long>();
        for (Section s : sections) {
            for (Question q : s.getQuestions()) {
                questionIds.add(q.getId());
            }
        }
        
        EntityManager em = EMF.get();

        Map<AnswerModel, Map<Long, Double>> answers = getAnswers(em, sections);
        
        int numSamples = answers.keySet().size();
        int numQuestions = questionIds.size();
        
        double[] y = new double[numSamples];
        double[][] x = new double[numSamples][numQuestions];
        
        int i = 0;
        for (Map.Entry<AnswerModel, Map<Long, Double>> entries : answers.entrySet()) {
            y[i] = entries.getKey().getResult();
            for (int j = 0; j < numQuestions; j++) {
                x[i][j] = entries.getValue().get(questionIds.get(j));
            }
            i++;
        }
        
        OLSMultipleLinearRegression reg = new OLSMultipleLinearRegression();
        reg.newSampleData(y, x);
        
        double[] beta = reg.estimateRegressionParameters();

        RegressionResultModel rr = new RegressionResultModel();
        rr.setSubmitted(new Date());
        rr.setIntercept(beta[beta.length-1]);

        List<RegressionCoefficientModel> rcs = new ArrayList<RegressionCoefficientModel>();
        for (int j = 0; j < numQuestions; j++) {
            RegressionCoefficientModel rc = new RegressionCoefficientModel();
            rc.setQuestionModelId(questionIds.get(j));
            rc.setValue(beta[j]);
            rc.setRegressionResult(rr);
            rcs.add(rc);
        }

        rr.setRegressionCoefficients(rcs);
        
        EMF.save(em,  rr);
        
        em.close();
    }

    @Override
    public Integer getNumSamples() {
        EntityManager em = EMF.get();
        Query q = em.createQuery ("SELECT count(x) FROM AnswerModel x");
        Number result = (Number) q.getSingleResult ();
        em.close();
        return result.intValue();
    }
    
    @Override
    public List<Section> getAllSectionsDeeplyWithWeights() {
        List<Section> sections = getAllSections(true);
        
        EntityManager em = EMF.get();
        Query query = em.createQuery("SELECT result FROM RegressionResultModel result " 
                + "ORDER BY result.submitted desc");
        query.setMaxResults(1);
        
        RegressionResultModel result = (RegressionResultModel) query.getResultList().get(0);
        
        Map<Long, Double> valueMap = new HashMap<Long, Double>();
        for (RegressionCoefficientModel rcm : result.getRegressionCoefficients())
        {
            valueMap.put(rcm.getQuestionModelId(), rcm.getValue());
        }
        em.close();
        
        for (Section s : sections) {
            for (Question q : s.getQuestions()) {
                q.setCoefficient(valueMap.get(q.getId()));
            }
        }
        
        return sections;
    }

    @Override
    public List<Country> getAllCountries() {
        EntityManager em = EMF.get();
        Query q = em.createQuery ("SELECT DISTINCT x.country FROM CropAllModel x", String.class);
        List<String> result = q.getResultList();
        
        List<Country> countryList = new ArrayList<Country>();
        for (String r : result) {
            Country c = new Country();
            c.setName(r);
            countryList.add(c);
        }
        
        em.close();
        
        return countryList;
    }

    @Override
    public List<Zone> getZonesByCountry(Country country) {
        EntityManager em = EMF.get();
        Query q = em.createQuery ("SELECT DISTINCT x.zone FROM CropAllModel x WHERE x.country = '" + country.getName() + "'", String.class);
        List<String> result = q.getResultList();
        
        List<Zone> zoneList = new ArrayList<Zone>();
        for (String r : result) {
            Zone c = new Zone();
            c.setName(r);
            zoneList.add(c);
        }

        
        
        em.close();
        
        return zoneList;
    }

    @Override
    public List<Crop> getCropsByCountryAndZone(Country country, Zone zone) {
        EntityManager em = EMF.get();
        Query q = em.createQuery ("SELECT x FROM CropAllModel x WHERE x.country = '" + country.getName() + "' and x.zone = '" + zone.getName() + "'", CropAllModel.class);
        List<CropAllModel> result = q.getResultList();
        
        List<Crop> cropList = new ArrayList<Crop>();
        for (CropAllModel r : result) {
            Crop c = new Crop();
            c.setName(r.getCrop());
            c.setId(r.getId());
            cropList.add(c);
        }
        
        em.close();
        
        return cropList;
    }

    @Override
    public List<CropPeriod> getCropPeriodsById(Long id) {
        EntityManager em = EMF.get();
        Query q = em.createQuery ("SELECT x FROM CropPeriodModel x WHERE x.id = " + id.toString(), CropPeriodModel.class);
        List<CropPeriodModel> result = q.getResultList();
        
        List<CropPeriod> cropList = new ArrayList<CropPeriod>();
        for (CropPeriodModel r : result) {
            CropPeriod c = new CropPeriod(r);
            cropList.add(c);
        }
        
        em.close();
        
        return cropList;
    }

}
