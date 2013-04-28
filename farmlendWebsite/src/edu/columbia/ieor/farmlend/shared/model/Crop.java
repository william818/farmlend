package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;
import java.util.List;

public class Crop implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Crop() {
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CropPeriod> getCropPeriods() {
        return cropPeriods;
    }

    public void setCropPeriods(List<CropPeriod> cropPeriods) {
        this.cropPeriods = cropPeriods;
    }

    private String name;
    private List<CropPeriod> cropPeriods;
    
}
