package edu.columbia.ieor.farmlend.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.DecoratedTabPanel;

public class Playground extends Composite {

    public Playground() {
        
        FlowPanel flowPanel = new FlowPanel();
        initWidget(flowPanel);
        
        DecoratedTabPanel decoratedTabPanel = new DecoratedTabPanel();
        flowPanel.add(decoratedTabPanel);
        
        FlowPanel flowPanel_1 = new FlowPanel();
        decoratedTabPanel.add(flowPanel_1, "How's it going?", false);
        flowPanel_1.setSize("5cm", "3cm");
        
        Label lblThisIsA = new Label("This is a label");
        flowPanel_1.add(lblThisIsA);
    }

}
