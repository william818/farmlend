package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "answer_choices")
public class AnswerChoiceModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public AnswerChoiceModel() {
    }
    
    public AnswerModel getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerModel answer) {
        this.answer = answer;
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

    private AnswerModel answer;
    
    private Long questionModelId;
    
    private Long choiceModelId;
}
