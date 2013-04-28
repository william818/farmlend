package edu.columbia.ieor.farmlend.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;

import edu.columbia.ieor.farmlend.shared.model.Choice;
import edu.columbia.ieor.farmlend.shared.model.Question;
import edu.columbia.ieor.farmlend.shared.model.Section;

public class ScoringToolComposite extends Composite {
    
    private static final String[] GEO_VALUES = new String[] { "East Asia and Pacific", "Latin America and Carribean", "Eastern Europe and Central Asia", "Middle East and North Africa", "South Asia", "Sub-Saharan Africa"};
    
    private Grid geoGrid = new Grid(1, 4);
    
    private static final NumberFormat decimalFormat = NumberFormat.getFormat("#.##");

    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);
    
    private static final String SUCCESS_TEXT = "Farm Lend Score: ";
    
    private Map<Long, ListBox> listBoxes = new HashMap<Long, ListBox>();
    
    private List<Section> sections;
    FlowPanel flowPanel = new FlowPanel();
    
    Button btnLend = new Button("Calculate Farm Lend Score");
    Label statusLabel = new Label("");
    Label loadingLabel = new Label("Loading, please wait...");

    
    public ScoringToolComposite() {
        
        initWidget(flowPanel);
        
        btnLend.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                calculateScore();
            }
        });
        
        flowPanel.add(loadingLabel);
        
        customLoad();
    }
    
    public void customLoad() {
        greetingService.getAllSectionsDeeplyWithWeights(new AsyncCallback<List<Section>>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(List<Section> result) {
                
                flowPanel.remove(loadingLabel);
                
                sections = result;
                drawEverything();
                
                Grid grid = new Grid(1, 3);
                flowPanel.add(grid);
                
                grid.setWidget(0, 0, btnLend);
                
                Label lblTnyn = new Label("");
                grid.setWidget(0, 1, lblTnyn);
                lblTnyn.setWidth("60px");
                
                flowPanel.add(statusLabel);
            }
        });
    }
    
    public void drawEverything() {
        
        for (Section s : sections) {
            if (s.getQuestions() != null && s.getQuestions().size() > 0)
                flowPanel.add(drawSection(s));
        }
    }
    
    private void addInGeoSelector() {
        Label geoPrompt = new Label("Please select your geography: ");
        ListBox geoSelections = new ListBox();
        for (String geoValue : GEO_VALUES) {
            geoSelections.addItem(geoValue, geoValue);
        }
        geoGrid.setWidget(0, 0, geoPrompt);
        geoGrid.setWidget(0, 1, geoSelections);
        
        flowPanel.add(geoGrid);
    }
    
    private CaptionPanel drawSection(Section s) {
        
        CaptionPanel captionPanel = new CaptionPanel(s.getName());
        Grid grid = new Grid(s.getQuestions().size(), 2);
        captionPanel.setContentWidget(grid);
        grid.setSize("100%", "100%");

        for (int i = 0; i < s.getQuestions().size(); i++) {
            Question q = s.getQuestions().get(i);
            
            Label label = new Label(q.getDisplayText());
            grid.setWidget(i, 0, label);
            
            ListBox comboBox = new ListBox();
            
            for (int j = 0; j < q.getChoices().size(); j++) {
                Choice cm = q.getChoices().get(j);
                comboBox.addItem(cm.getDisplayText(), cm.getId().toString());
            }
            
            // allow user to change selection
            //comboBox.setEnabled(false);
            
            listBoxes.put(q.getId(), comboBox);
            
            grid.setWidget(i, 1, comboBox);
            comboBox.setWidth("100%");
            grid.getCellFormatter().setVerticalAlignment(i, 1, HasVerticalAlignment.ALIGN_TOP);
            grid.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_LEFT);
            grid.getCellFormatter().setVerticalAlignment(i, 0, HasVerticalAlignment.ALIGN_TOP);
            grid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_LEFT);
        }
        
        return captionPanel;
    }
    
    private void calculateScore() {
        btnLend.setEnabled(false);
        
        double currentScore = 0.0;
        
        for (Section s : sections) {
            for (Question q : s.getQuestions()) {
                ListBox currentBox = listBoxes.get(q.getId());
                currentScore += q.getCoefficient() * Double.parseDouble(currentBox.getValue(currentBox.getSelectedIndex()));
            }
        }
        
        statusLabel.setText(SUCCESS_TEXT + (int)(currentScore*1000));
        statusLabel.setStyleName("statusGreenScore");
        btnLend.setEnabled(true);
    }

}
