package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;


public class Choice implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    public Long getId() {
        return id;
    }

    public Choice() {
    }
    
    public Choice(ChoiceModel cm) {
        id = cm.getId();
        displayText = cm.getDisplayText();
        value = cm.getValue();
    }
    
    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    private String displayText;
    
    private Double value;
}
