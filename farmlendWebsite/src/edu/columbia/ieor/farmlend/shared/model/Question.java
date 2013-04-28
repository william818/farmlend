package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Question implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    public Long getId() {
        return id;
    }

    public Question() {
    }
    
    public Question(QuestionModel qm) {
        id = qm.getId();
        displayText = qm.getDisplayText();
        choices = new ArrayList<Choice>();
        for (ChoiceModel cm : qm.getChoices())
            choices.add(new Choice(cm));
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    private List<Choice> choices;
    private String displayText;
    private String token;
    
    private Double coefficient;

}
