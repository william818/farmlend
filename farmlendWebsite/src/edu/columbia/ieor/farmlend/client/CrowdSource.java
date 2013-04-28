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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;

import edu.columbia.ieor.farmlend.shared.model.Answer;
import edu.columbia.ieor.farmlend.shared.model.AnswerChoice;
import edu.columbia.ieor.farmlend.shared.model.Choice;
import edu.columbia.ieor.farmlend.shared.model.Question;
import edu.columbia.ieor.farmlend.shared.model.Section;

public class CrowdSource extends DialogBox {

    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);
    
    private Map<Long, ListBox> listBoxes = new HashMap<Long, ListBox>();
    
    private List<Section> sections;
    FlowPanel flowPanel = new FlowPanel();
    
    public CrowdSource() {
        setHTML("Crowd Source");
        
        ScrollPanel scrollPanel = new ScrollPanel();
        setWidget(scrollPanel);
        scrollPanel.setSize("405px", "339px");
        
        scrollPanel.setWidget(flowPanel);
        flowPanel.setSize("100%", "100%");
        
        customLoad();
        randomizeChoices();
    }

    public void customLoad() {
        greetingService.getAllSectionsDeeply(new AsyncCallback<List<Section>>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(List<Section> result) {
                sections = result;
                drawEverything();
                
                Button btnLend = new Button("Lend!");
                btnLend.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        saveVote(1);
                        randomizeChoices();
                    }
                });
                
                Button btnDontLend = new Button("Don't Lend!");
                btnDontLend.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        saveVote(0);
                        randomizeChoices();
                    }
                });

                flowPanel.add(btnLend);
                flowPanel.add(btnDontLend);
            }
        });
    }
    
    public void saveVote(Integer voteValue) {
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
            }

            public void onSuccess(Void result) {
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
            
            comboBox.setEnabled(false);
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
