package edu.columbia.ieor.farmlend.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.columbia.ieor.farmlend.shared.model.Answer;
import edu.columbia.ieor.farmlend.shared.model.Country;
import edu.columbia.ieor.farmlend.shared.model.Crop;
import edu.columbia.ieor.farmlend.shared.model.CropPeriod;
import edu.columbia.ieor.farmlend.shared.model.QuestionModel;
import edu.columbia.ieor.farmlend.shared.model.Section;
import edu.columbia.ieor.farmlend.shared.model.Zone;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
    void greetServer(String input, AsyncCallback<String> callback)
            throws IllegalArgumentException;

    void addSection(String sectionName, AsyncCallback<Void> callback);

    void getAllSections(AsyncCallback<List<Section>> callback);

    void addQuestion(QuestionModel question, AsyncCallback<Void> callback);

    void getAllSectionsDeeply(AsyncCallback<List<Section>> callback);

    void saveAnswer(Answer answer, AsyncCallback<Void> callback);

    void runRegression(AsyncCallback<Void> callback);

    void getNumSamples(AsyncCallback<Integer> callback);

    void getAllSectionsDeeplyWithWeights(AsyncCallback<List<Section>> callback);

    void getAllCountries(AsyncCallback<List<Country>> callback);

    void getZonesByCountry(Country country, AsyncCallback<List<Zone>> callback);

    void getCropsByCountryAndZone(Country country, Zone zone,
            AsyncCallback<List<Crop>> callback);

    void getCropPeriodsById(Long id, AsyncCallback<List<CropPeriod>> callback);
}
