package com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate;

import com.crewmeister.cmcodingchallenge.currency.entities.Currency.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Embeddable
@Getter
@Setter
public class CurrencyExchangeRateId implements Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(name="currency_id", nullable=false)
    private Currency currency;

    @Column(name="date", nullable = false)
    private String date;
    public CurrencyExchangeRateId() {
    }
    public CurrencyExchangeRateId(Currency currency, String date) {
        this.currency = currency;
        this.date = date;
    }
    @Override
    public String toString() {
        return "CurrencyExchangeRateId{" +
                "currency=" + currency +
                ", date='" + date + '\'' +
                '}';
    }

    public String toJsonString(){
        return "{\"currency\":"+currency.toJsonString()+", \"date\":"+"\""+date+"\"}";
    }
}
