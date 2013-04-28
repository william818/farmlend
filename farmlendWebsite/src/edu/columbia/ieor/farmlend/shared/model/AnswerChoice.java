package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;

public class AnswerChoice implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    public Long getId() {
        return id;
    }

    public AnswerChoice() {
    }

    public Long getQuestionModelId() {
        return questionModelId;
    }

    public void setQuestionModelId(Long questionModelId) {
        this.questionModelId = questionModelId;
    }

    public Long getChoiceModelId() {
        return choiceModelId;
    }

    public void setChoiceModelId(Long choiceModelId) {
        this.choiceModelId = choiceModelId;
    }

    private Long questionModelId;
    
    private Long choiceModelId;
}
