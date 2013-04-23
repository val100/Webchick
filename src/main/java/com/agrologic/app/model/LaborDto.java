
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.model;

/**
 *
 * @author JanL
 */
public class LaborDto {
    private String  date;
    private String  define;
    private Long    flockId;
    private Integer hours;
    private Long    id;
    private Float   salary;
    private Long    workerId;

    public LaborDto() {}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDefine() {
        return define;
    }

    public void setDefine(String define) {
        this.define = define;
    }

    public Long getFlockId() {
        return flockId;
    }

    public void setFlockId(Long flockId) {
        this.flockId = flockId;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final LaborDto other = (LaborDto) obj;

        if ((this.id != other.id) && ((this.id == null) ||!this.id.equals(other.id))) {
            return false;
        }

        if ((this.flockId != other.flockId) && ((this.flockId == null) ||!this.flockId.equals(other.flockId))) {
            return false;
        }

        if ((this.workerId != other.workerId) && ((this.workerId == null) ||!this.workerId.equals(other.workerId))) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;

        hash = 29 * hash + ((this.id != null)
                            ? this.id.hashCode()
                            : 0);
        hash = 29 * hash + ((this.flockId != null)
                            ? this.flockId.hashCode()
                            : 0);
        hash = 29 * hash + ((this.workerId != null)
                            ? this.workerId.hashCode()
                            : 0);

        return hash;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
