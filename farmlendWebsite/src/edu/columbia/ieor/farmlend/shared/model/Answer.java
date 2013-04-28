package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    public Long getId() {
        return id;
    }

    public Answer() {
    }
    
    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AnswerChoice> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<AnswerChoice> answerChoices) {
        this.answerChoices = answerChoices;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date dateSubmitted) {
        this.submitted = dateSubmitted;
    }

    private Integer result;
    
    private String description;
    
    private Date submitted;
    
    private List<AnswerChoice> answerChoices;
}
