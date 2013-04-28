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
@Table(name = "regressions")
public class RegressionResultModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public RegressionResultModel() {
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date dateSubmitted) {
        this.submitted = dateSubmitted;
    }

    public List<RegressionCoefficientModel> getRegressionCoefficients() {
        return regressionCoefficients;
    }

    public void setRegressionCoefficients(List<RegressionCoefficientModel> regressionCoefficients) {
        this.regressionCoefficients = regressionCoefficients;
    }

    public Double getIntercept() {
        return intercept;
    }

    public void setIntercept(Double intercept) {
        this.intercept = intercept;
    }

    private Date submitted;
    
    private Double intercept;
    
    @OneToMany(mappedBy = "regressionResult", targetEntity = RegressionCoefficientModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegressionCoefficientModel> regressionCoefficients;
}
