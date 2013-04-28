package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Section implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    public Long getId() {
        return id;
    }

    public Section() {
        
    }
    
    public Section(SectionModel sm) {
        this(sm, true);
    }

    public Section(SectionModel sm, boolean iterate) {
        id = sm.getId();
        name = sm.getName();
        questions = new ArrayList<Question>();
        if (iterate)
            for (QuestionModel qm : sm.getQuestions())
                questions.add(new Question(qm));
    }
    
    private String name;
    
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }    

}
