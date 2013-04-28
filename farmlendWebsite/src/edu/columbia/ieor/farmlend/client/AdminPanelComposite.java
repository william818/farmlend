package edu.columbia.ieor.farmlend.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;

public class AdminPanelComposite extends Composite {
    
    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);
    
    Label lblThereAreCurrently = new Label("Loading...");
    
    public AdminPanelComposite() {
        
        TabPanel tabPanel = new TabPanel();
        FlowPanel mainFlow = new FlowPanel();
        initWidget(mainFlow);
        mainFlow.add(tabPanel);
        tabPanel.setSize("100%", "100%");
        tabPanel.setAnimationEnabled(true);
        
        FlowPanel flowPanel_1 = new FlowPanel();
        tabPanel.add(flowPanel_1, "Scoring Tool", false);
        flowPanel_1.setSize("100%", "100%");
        
        flowPanel_1.add(new ScoringToolComposite());
        
        FlowPanel flowPanel_4 = new FlowPanel();
        tabPanel.add(flowPanel_4, "Recommendation Tool", false);
        flowPanel_4.setSize("100%", "100%");
        flowPanel_4.add(new CropCalendarComposite());
        
        FlowPanel flowPanel_2 = new FlowPanel();
        tabPanel.add(flowPanel_2, "Coefficient Optimization", false);
        flowPanel_2.setSize("100%", "100%");
        flowPanel_2.add(lblThereAreCurrently);

        flowPanel_2.add(new CoefficientDisplayComposite());
        
        FlowPanel flowPanel = new FlowPanel();
        tabPanel.add(flowPanel, "Add Section", false);
        flowPanel.setSize("100%", "100%");
        flowPanel.add(new EditSectionsComposite());
                
        
        FlowPanel flowPanel_3 = new FlowPanel();
        tabPanel.add(flowPanel_3, "Add Question", false);
        flowPanel_3.setSize("100%", "100%");
        flowPanel_3.add(new AddQuestionComposite());
        
        tabPanel.selectTab(0);
        
        greetingService.getNumSamples(new AsyncCallback<Integer>() {
            public void onFailure(Throwable caught) {
                setSamples(-1);
            }

            public void onSuccess(Integer result) {
                setSamples(result);
            }
        });
    }
    
    private void setSamples(int n) {
        lblThereAreCurrently.setText("There are currently " + n + " samples.");
        lblThereAreCurrently.setStyleName("statusBold");
    }

}
