package edu.columbia.ieor.farmlend.shared.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CROP_PERIODS")
public class CropPeriodModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public CropPeriodModel() {
    }

    private Integer startday;
    private Integer startmonth;
    private Integer endday;
    private Integer endmonth;
    private Integer colorid;

    public Integer getStartday() {
        return startday;
    }

    public void setStartday(Integer startday) {
        this.startday = startday;
    }

    public Integer getStartmonth() {
        return startmonth;
    }

    public void setStartmonth(Integer startmonth) {
        this.startmonth = startmonth;
    }

    public Integer getEndday() {
        return endday;
    }

    public void setEndday(Integer endday) {
        this.endday = endday;
    }

    public Integer getEndmonth() {
        return endmonth;
    }

    public void setEndmonth(Integer endmonth) {
        this.endmonth = endmonth;
    }

    public Integer getColorid() {
        return colorid;
    }

    public void setColorid(Integer colorid) {
        this.colorid = colorid;
    }

}
