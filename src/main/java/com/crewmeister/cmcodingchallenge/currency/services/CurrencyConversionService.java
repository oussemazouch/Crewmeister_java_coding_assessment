package com.crewmeister.cmcodingchallenge.currency.services;

import com.crewmeister.cmcodingchallenge.currency.entities.Currency.Currency;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRate;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRateId;
import com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos.CurrencyConversionData;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyExchangeRateRepo;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConversionService {

    @Autowired
    private CurrencyExchangeRateRepo currencyExchangeRepo;
    public CurrencyConversionService(CurrencyExchangeRateRepo currencyExchangeRepo) {
        this.currencyExchangeRepo = currencyExchangeRepo;
    }

    public ResponseEntity<String> convertToEuro(CurrencyConversionData conversionData) {
        try {
            CurrencyExchangeRate exchangeRate = findExchangeRate(conversionData);

            if (exchangeRate == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            String convertedAmount = formatConversionMessage(conversionData, exchangeRate);
            return new ResponseEntity<>(convertedAmount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CurrencyExchangeRate findExchangeRate(CurrencyConversionData conversionData) {
        return currencyExchangeRepo.findByCurrencyExchangeRateId(
                new CurrencyExchangeRateId(new Currency(conversionData.getCurrency()), conversionData.getDate())
        );
    }

    public String formatConversionMessage(CurrencyConversionData conversionData, CurrencyExchangeRate exchangeRate) {
        double convertedAmount = conversionData.getAmount() / exchangeRate.getExchangeRate();
        return String.format("%s %s ≈ %.2f €", conversionData.getAmount(), conversionData.getCurrency(), convertedAmount);
    }
}