
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.model;

/**
 *
 * @author JanL
 */
public class TransactionDto {
    private Float  expances;
    private Long   flockId;
    private Long   id;
    private String name;
    private Float  revenues;

    public TransactionDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getExpenses() {
        return expances;
    }

    public void setExpenses(Float expances) {
        this.expances = expances;
    }

    public Long getFlockId() {
        return flockId;
    }

    public void setFlockId(Long flockId) {
        this.flockId = flockId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getRevenues() {
        return revenues;
    }

    public void setRevenues(Float revenues) {
        this.revenues = revenues;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final TransactionDto other = (TransactionDto) obj;

        if ((this.id != other.id) && ((this.id == null) ||!this.id.equals(other.id))) {
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

        return hash;
    }

    @Override
    public String toString() {
        return "TransactionDto{" + "id=" + id + "flockId=" + flockId + "revenues=" + revenues + "expances=" + expances
               + '}';
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
