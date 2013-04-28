package edu.columbia.ieor.farmlend.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.columbia.ieor.farmlend.shared.model.Answer;
import edu.columbia.ieor.farmlend.shared.model.Country;
import edu.columbia.ieor.farmlend.shared.model.Crop;
import edu.columbia.ieor.farmlend.shared.model.CropPeriod;
import edu.columbia.ieor.farmlend.shared.model.QuestionModel;
import edu.columbia.ieor.farmlend.shared.model.Section;
import edu.columbia.ieor.farmlend.shared.model.Zone;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
    String greetServer(String name) throws IllegalArgumentException;
    
    void addSection(String sectionName);
    
    List<Section> getAllSections();

    List<Section> getAllSectionsDeeply();
    
    void addQuestion(QuestionModel question);
    
    void saveAnswer(Answer answer);
    
    void runRegression();
    
    Integer getNumSamples();
    
    List<Section> getAllSectionsDeeplyWithWeights();
    
    // BELOW ARE FOR RECOMMENDATION ENGINE
    
    List<Country> getAllCountries();
    List<Zone> getZonesByCountry(Country country);
    List<Crop> getCropsByCountryAndZone(Country country, Zone zone);
    List<CropPeriod> getCropPeriodsById(Long id);
}
