package com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyConversionData {
    private double amount;
    private String date;
    private String currency;

    public CurrencyConversionData() {
    }

    public CurrencyConversionData(double amount, String date, String currency) {
        this.amount = amount;
        this.date = date;
        this.currency = currency;
    }

}

