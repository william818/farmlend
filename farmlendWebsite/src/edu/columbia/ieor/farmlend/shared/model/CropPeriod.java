package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;

public class CropPeriod implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public CropPeriod() {
    }

    public CropPeriod(CropPeriodModel m) {
        this.color = m.getColorid();
        this.endDay = m.getEndday();
        this.endMonth = m.getEndmonth();
        this.startDay = m.getStartday();
        this.startMonth = m.getStartmonth();
    }
    
    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    private Integer startDay;
    private Integer startMonth;
    private Integer endMonth;
    private Integer endDay;
    private Integer color;
    
}
