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

import edu.columbia.ieor.farmlend.shared.model.Question;
import edu.columbia.ieor.farmlend.shared.model.Section;

public class CoefficientDisplayComposite extends Composite {

    private static final NumberFormat decimalFormat = NumberFormat.getFormat("#.##");
    
    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);
    
    private static final String SUCCESS_TEXT = "Optimal coefficients have been re-calculated.";
    private static final String ERROR_TEXT = "An error occured.";
    
    private Map<Long, Label> coefficientBoxes = new HashMap<Long, Label>();
    
    private List<Section> sections;
    FlowPanel flowPanel = new FlowPanel();
    
    Button btnLend = new Button("Re-Calculate Optimal Coefficients");
    Label statusLabel = new Label("");
    Label loadingLabel = new Label("Loading, please wait...");

    
    public CoefficientDisplayComposite() {
        
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
    
    private CaptionPanel drawSection(Section s) {
        
        CaptionPanel captionPanel = new CaptionPanel(s.getName());
        Grid grid = new Grid(s.getQuestions().size(), 2);
        captionPanel.setContentWidget(grid);
        grid.setSize("100%", "100%");

        for (int i = 0; i < s.getQuestions().size(); i++) {
            Question q = s.getQuestions().get(i);
            
            Label label = new Label(q.getDisplayText());
            grid.setWidget(i, 0, label);

            Label label2 = new Label(decimalFormat.format(q.getCoefficient()*100.0));
            grid.setWidget(i, 1, label2);
            
            coefficientBoxes.put(q.getId(), label2);
            
            grid.getCellFormatter().setVerticalAlignment(i, 1, HasVerticalAlignment.ALIGN_TOP);
            grid.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_LEFT);
            grid.getCellFormatter().setVerticalAlignment(i, 0, HasVerticalAlignment.ALIGN_TOP);
            grid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_LEFT);
        }
        
        return captionPanel;
    }
    
    private void calculateScore() {
        
        btnLend.setEnabled(false);
        
        greetingService.runRegression(new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                statusLabel.setText(ERROR_TEXT);
                statusLabel.setStyleName("statusRed");
                btnLend.setEnabled(true);
            }

            public void onSuccess(Void result) {
                reloadNewScores();
            }
        });

    }
    
    private void reloadNewScores() {
        
        greetingService.getAllSectionsDeeplyWithWeights(new AsyncCallback<List<Section>>() {
            public void onFailure(Throwable caught) {
                statusLabel.setText(ERROR_TEXT);
                statusLabel.setStyleName("statusRed");
                btnLend.setEnabled(true);
            }

            public void onSuccess(List<Section> result) {
                for (Section s : result) {
                    for (Question q : s.getQuestions()) {
                        coefficientBoxes.get(q.getId()).setText(decimalFormat.format(q.getCoefficient()*100.0));
                    }
                }
                statusLabel.setText(SUCCESS_TEXT);
                statusLabel.setStyleName("statusGreen");
                btnLend.setEnabled(true);
            }
        });
    }

}
