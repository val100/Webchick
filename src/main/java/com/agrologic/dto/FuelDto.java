
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.dto;

/**
 *
 * @author JanL
 */
public class FuelDto {
    private Integer amount;
    private String  date;
    private Long    flockId;
    private Long    id;
    private Integer numberAccount;
    private Float   price;
    private Float   total;

    public FuelDto() {}

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public Integer getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(Integer numberAccount) {
        this.numberAccount = numberAccount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
