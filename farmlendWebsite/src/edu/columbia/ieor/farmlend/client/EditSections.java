package edu.columbia.ieor.farmlend.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class EditSections extends DialogBox {

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT
            .create(GreetingService.class);

    TextBox textBox = new TextBox();
    Button btnAdd = new Button("Add");
    
    public EditSections() {
        setText("Add Section");
        setAnimationEnabled(true);
        
        setHTML("Add Section");
        
        FlowPanel flowPanel = new FlowPanel();
        setWidget(flowPanel);
        flowPanel.setSize("355px", "96px");
        
        Label lblSectionName = new Label("Section Name: ");
        flowPanel.add(lblSectionName);
        lblSectionName.setWidth("157px");
        
        flowPanel.add(textBox);
        textBox.setWidth("338px");
        
        btnAdd.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                addSection();
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
                hide();
            }

            public void onSuccess(Void result) {
                hide();
            }
        });

    }
    
}
