package com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Table(name="currency_rate")
@Entity
@Getter
@Setter
public class CurrencyExchangeRate {
    @EmbeddedId
    private CurrencyExchangeRateId currencyExchangeRateId;

    @Column(name="exchange_rate")
    private float exchangeRate;


    public CurrencyExchangeRate() {
    }

    public CurrencyExchangeRate(CurrencyExchangeRateId currencyExchangeRateId, float exchangeRate) {
        this.currencyExchangeRateId = currencyExchangeRateId;
        this.exchangeRate = exchangeRate;
    }
    @Override
    public String toString() {
        return "CurrencyExchangeRate{" +
                "currencyExchangeRateId=" + currencyExchangeRateId +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}
