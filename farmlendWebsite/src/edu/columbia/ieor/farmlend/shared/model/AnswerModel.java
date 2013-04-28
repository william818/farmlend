package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "answers")
public class AnswerModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public AnswerModel() {
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

    public List<AnswerChoiceModel> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<AnswerChoiceModel> answerChoices) {
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
    
    @OneToMany(mappedBy = "answer", targetEntity = AnswerChoiceModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AnswerChoiceModel> answerChoices;
}
