package com.crewmeister.cmcodingchallenge.currency.entities.Currency;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="currency")
@Entity
@Setter
@Getter
public class Currency{

    @Id
    @Column(name = "currency_id")
    private String currencyId;

    public Currency() {
    }

    public Currency(String currencyId) {
        this.currencyId = currencyId;
    }
    @Override
    public String toString() {
        return "Currency{" +
                "currencyId='" + currencyId + '\'' +
                '}';
    }
    public String toJsonString(){
        return "{\"currencyId\":"+"\""+currencyId+"\"}";
    }
}
