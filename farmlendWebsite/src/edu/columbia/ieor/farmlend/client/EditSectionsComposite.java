package edu.columbia.ieor.farmlend.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class EditSectionsComposite extends Composite {

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);

    TextBox textBox = new TextBox();
    Button btnAdd = new Button("Add");
    
    public EditSectionsComposite() {
        
        FlowPanel flowPanel = new FlowPanel();
        initWidget(flowPanel);
        flowPanel.setSize("355px", "96px");
        
        Label lblSectionName = new Label("Section Name: ");
        flowPanel.add(lblSectionName);
        lblSectionName.setWidth("157px");
        
        flowPanel.add(textBox);
        textBox.setWidth("338px");
        
        btnAdd.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //addSection();
                showWarningBox();
            }

        });
        flowPanel.add(btnAdd);
        btnAdd.setWidth("99px");
    }

    private void addSection() {
        btnAdd.setEnabled(false);
        greetingService.addSection(textBox.getText(),
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
        dialogBox.setText("Not Adding Your Section");
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
