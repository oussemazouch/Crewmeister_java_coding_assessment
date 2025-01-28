package com.crewmeister.cmcodingchallenge.currency.services;

import com.crewmeister.cmcodingchallenge.currency.entities.Currency.Currency;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRate;
import com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos.CurrencyConversionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyManagementService currencyManagementService;

    @Autowired
    private ExchangeRateManagementService exchangeRateManagementService;

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @Autowired
    private DataFetchingService dataFetchingService;

    public ResponseEntity<Currency> createCurrency(Currency newCurrency) {
        return currencyManagementService.createCurrency(newCurrency);
    }

    public ResponseEntity<List<String>> getAllCurrencies() {
        return currencyManagementService.getAllCurrencies();
    }

    public ResponseEntity<CurrencyExchangeRate> createExchangeRate(CurrencyExchangeRate newExchangeRate) {
        return exchangeRateManagementService.createExchangeRate(newExchangeRate);
    }

    public ResponseEntity<List<CurrencyExchangeRate>> getAllExchangeRates() {
        return exchangeRateManagementService.getAllExchangeRates();
    }

    public ResponseEntity<List<CurrencyExchangeRate>> getExchangeRatesByDate(String date) {
        return exchangeRateManagementService.getExchangeRatesByDate(date);
    }

    public ResponseEntity<String> convertToEuro(CurrencyConversionData conversionData) {
        return currencyConversionService.convertToEuro(conversionData);
    }

    public String initializeDB(String startDate) {
        return dataFetchingService.initializeDB(startDate);
    }

    @Scheduled(cron = "0 1 10 * * ?", zone = "UTC")
    public void updateDB() {
        dataFetchingService.updateDB();
    }
}