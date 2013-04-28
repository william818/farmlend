package edu.columbia.ieor.farmlend.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.columbia.ieor.farmlend.shared.model.ChoiceModel;
import edu.columbia.ieor.farmlend.shared.model.QuestionModel;
import edu.columbia.ieor.farmlend.shared.model.Section;
import edu.columbia.ieor.farmlend.shared.model.SectionModel;

public class AddQuestionComposite extends Composite {

    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);

    List<Section> sections;
    ListBox comboBox = new ListBox();
    Grid grid = new Grid(1, 2);
    
    List<TextBox> displayTextBoxes = new ArrayList<TextBox>();
    List<TextBox> valueBoxes = new ArrayList<TextBox>();
    Button btnAddQuestion = new Button("Add Question");
    TextBox questionBox = new TextBox();
    
    public AddQuestionComposite() {
        
        FlowPanel flowPanel = new FlowPanel();
        initWidget(flowPanel);
        flowPanel.setSize("453px", "482px");
        
        Label lblSection = new Label("Section:");
        flowPanel.add(lblSection);
        
        flowPanel.add(comboBox);
        comboBox.setWidth("370px");
        
        Label lblPrompt = new Label("Prompt: ");
        flowPanel.add(lblPrompt);
        
        flowPanel.add(questionBox);
        questionBox.setWidth("362px");
        
        Label lblResponses = new Label("Responses:");
        flowPanel.add(lblResponses);
        
        ScrollPanel scrollPanel = new ScrollPanel();
        flowPanel.add(scrollPanel);
        scrollPanel.setSize("372px", "257px");
        
        scrollPanel.setWidget(grid);
        grid.setSize("100%", "100%");
        
        Label lblDisplayText = new Label("Display Text:");
        grid.setWidget(0, 0, lblDisplayText);
        
        Label lblValue = new Label("Value:");
        grid.setWidget(0, 1, lblValue);
        
        Button btnAddNewResponse = new Button("Add New Response");
        btnAddNewResponse.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                addChoice();
            }
        });
        flowPanel.add(btnAddNewResponse);
        btnAddNewResponse.setWidth("373px");
        
        btnAddQuestion.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //addQuestion();
                showWarningBox();
            }
        });
        flowPanel.add(btnAddQuestion);   
        
        setupSections();
        addChoice();
    }

    private void addChoice() {
        int currentSize = grid.getRowCount();
        grid.resizeRows(currentSize + 1);

        TextBox displayBox1 = new TextBox();
        grid.setWidget(currentSize, 0, displayBox1);
        
        TextBox valueBox1 = new TextBox();
        grid.setWidget(currentSize, 1, valueBox1);

        displayTextBoxes.add(displayBox1);
        valueBoxes.add(valueBox1);
    }
    
    private void setupSections() {
        greetingService.getAllSections(new AsyncCallback<List<Section>>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(List<Section> result) {
                sections = result;
                for (int i = 0; i < sections.size(); i++) {
                    comboBox.addItem(sections.get(i).getName(), Integer.valueOf(i).toString());
                }
            }
        });
    }
    
    private void addQuestion() {
        btnAddQuestion.setEnabled(false);
        
        QuestionModel q = new QuestionModel();
        
        SectionModel sm = new SectionModel();
        sm.setId(sections.get(comboBox.getSelectedIndex()).getId());
        
        q.setSection(sm);
        q.setDisplayText(questionBox.getText());
        
        List<ChoiceModel> choices = new ArrayList<ChoiceModel>();
        for (int i = 0; i < displayTextBoxes.size(); i++) {
            ChoiceModel cm = new ChoiceModel();
            cm.setDisplayText(displayTextBoxes.get(i).getText());
            cm.setValue(Double.parseDouble(valueBoxes.get(i).getText()));
            //cm.setQuestion(q);
            choices.add(cm);
        }
        q.setChoices(choices);
        
        greetingService.addQuestion(q,
                new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(Void result) {
            }
        });

    }
    
    private void showWarningBox() {
        // Create the popup dialog box
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Not Adding Your Question");
        dialogBox.setAnimationEnabled(true);
        final Button closeButton = new Button("Close");
        // We can set the id of a widget by accessing its Element
        closeButton.getElement().setId("closeButton");
        VerticalPanel dialogVPanel = new VerticalPanel();
        dialogVPanel.addStyleName("dialogVPanel");
        dialogVPanel.add(new HTML("<b>Temporarily disabled to keep the environment consistent for our demo.</b>"));
        dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
        dialogVPanel.add(closeButton);
        dialogBox.setWidget(dialogVPanel);
        dialogBox.center();

        // Add a handler to close the DialogBox
        closeButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

    }
    
}
