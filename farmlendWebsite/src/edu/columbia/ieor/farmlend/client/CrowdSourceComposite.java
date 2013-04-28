package edu.columbia.ieor.farmlend.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

import edu.columbia.ieor.farmlend.shared.model.Answer;
import edu.columbia.ieor.farmlend.shared.model.AnswerChoice;
import edu.columbia.ieor.farmlend.shared.model.Choice;
import edu.columbia.ieor.farmlend.shared.model.Question;
import edu.columbia.ieor.farmlend.shared.model.Section;

public class CrowdSourceComposite extends Composite {

    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);
    
    private static final String SUCCESS_TEXT = "Your decision has been noted. Please evaluate this new set of attributes.";
    private static final String ERROR_TEXT = "An error occured.";
    
    private Map<Long, ListBox> listBoxes = new HashMap<Long, ListBox>();
    
    private List<Section> sections;
    FlowPanel flowPanel = new FlowPanel();
    
    Button btnLend = new Button("Lend");
    Button btnDontLend = new Button("Don't Lend");
    Label statusLabel = new Label("");
    Label loadingLabel = new Label("Loading, please wait...");

    
    public CrowdSourceComposite() {
        
        initWidget(flowPanel);
        
        btnLend.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                saveVote(1);
                randomizeChoices();
            }
        });
        
        btnDontLend.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                saveVote(0);
                randomizeChoices();
            }
        });

        flowPanel.add(loadingLabel);
        
        customLoad();
        randomizeChoices();
    }
    
    public void customLoad() {
        greetingService.getAllSectionsDeeply(new AsyncCallback<List<Section>>() {
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
                
                grid.setWidget(0, 2, btnDontLend);
                
                flowPanel.add(statusLabel);
            }
        });
    }
    
    public void saveVote(Integer voteValue) {
        // disable buttons
        btnDontLend.setEnabled(false);
        btnLend.setEnabled(false);
        statusLabel.setText("");
        
        Answer answer = new Answer();
        answer.setResult(voteValue);
        answer.setSubmitted(new Date());
        
        List<AnswerChoice> answerChoices = new ArrayList<AnswerChoice>();
        for(Map.Entry<Long, ListBox> box : listBoxes.entrySet()) {
            AnswerChoice answerChoice = new AnswerChoice();
            ListBox currentBox = box.getValue();
            answerChoice.setChoiceModelId(Long.parseLong(currentBox.getValue(currentBox.getSelectedIndex())));
            answerChoice.setQuestionModelId(box.getKey());
            answerChoices.add(answerChoice);
        }
        
        answer.setAnswerChoices(answerChoices);
        
        greetingService.saveAnswer(answer, 
                new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                btnDontLend.setEnabled(true);
                btnLend.setEnabled(true);
                statusLabel.setText(ERROR_TEXT);
                statusLabel.setStyleName("statusRed");
            }

            public void onSuccess(Void result) {
                btnDontLend.setEnabled(true);
                btnLend.setEnabled(true);
                statusLabel.setText(SUCCESS_TEXT);
                statusLabel.setStyleName("statusGreen");
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
    
    private void randomizeChoices() {
        for (ListBox l : listBoxes.values())
        {
            l.setSelectedIndex((int)(Math.random() * 100) % l.getItemCount());
        }
    }

}
