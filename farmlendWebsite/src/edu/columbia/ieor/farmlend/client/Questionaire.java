package edu.columbia.ieor.farmlend.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class Questionaire extends Composite {

    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);
    
    private List<Section> sections;
    FlowPanel flowPanel = new FlowPanel();
    
    public Questionaire() {
        
        initWidget(flowPanel);
        
        CaptionPanel captionPanel = new CaptionPanel("Generoo");
        flowPanel.add(captionPanel);
        
        Grid grid = new Grid(1, 2);
        captionPanel.setContentWidget(grid);
        grid.setSize("100%", "100%");
        
        Label label = new Label("New label");
        grid.setWidget(0, 0, label);
        
        ListBox comboBox = new ListBox();
        grid.setWidget(0, 1, comboBox);
        comboBox.setWidth("100%");
        grid.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
        grid.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        grid.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

        captionPanel.setVisible(false);
    }

    public void customLoad() {
        greetingService.getAllSections(new AsyncCallback<List<Section>>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(List<Section> result) {
                sections = result;
                drawEverything();
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
            
            ListBox comboBox = new ListBox();
            
            for (int j = 0; j < q.getChoices().size(); j++) {
                Choice cm = q.getChoices().get(j);
                comboBox.addItem(cm.getDisplayText(), Integer.valueOf(j).toString());
            }
            
            grid.setWidget(i, 1, comboBox);
            comboBox.setWidth("100%");
            grid.getCellFormatter().setVerticalAlignment(i, 1, HasVerticalAlignment.ALIGN_TOP);
            grid.getCellFormatter().setHorizontalAlignment(i, 1, HasHorizontalAlignment.ALIGN_LEFT);
            grid.getCellFormatter().setVerticalAlignment(i, 0, HasVerticalAlignment.ALIGN_TOP);
            grid.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_LEFT);
        }
        
        return captionPanel;
    }
    
    
}
