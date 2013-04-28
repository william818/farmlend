package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;
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
@Table(name = "questions")
public class QuestionModel implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public QuestionModel() {
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

    @OneToMany(mappedBy = "question", targetEntity = ChoiceModel.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public List<ChoiceModel> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceModel> choices) {
        this.choices = choices;
    }

    public SectionModel getSection() {
        return section;
    }

    public void setSection(SectionModel section) {
        this.section = section;
    }

    private List<ChoiceModel> choices;
    private String displayText;
    private String token;
    private SectionModel section;

}
