package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coefficients")
public class RegressionCoefficientModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public RegressionCoefficientModel() {
    }
    
    public RegressionResultModel getRegressionResult() {
        return regressionResult;
    }

    public void setRegressionResult(RegressionResultModel regressionResult) {
        this.regressionResult = regressionResult;
    }

    public Long getQuestionModelId() {
        return questionModelId;
    }

    public void setQuestionModelId(Long questionModelId) {
        this.questionModelId = questionModelId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    private RegressionResultModel regressionResult;
    
    private Long questionModelId;
    
    private Double value;
}
