package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;

public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Zone() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    
}
