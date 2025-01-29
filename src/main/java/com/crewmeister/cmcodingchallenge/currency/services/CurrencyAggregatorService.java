package com.crewmeister.cmcodingchallenge.currency.services;

import com.crewmeister.cmcodingchallenge.currency.entities.Currency.Currency;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRate;
import com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos.CurrencyConversionData;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyExchangeRateRepo;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CurrencyAggregatorService {

    @Autowired
    public CurrencyManagementService currencyManagementService;

    @Autowired
    public ExchangeRateManagementService exchangeRateManagementService;

    @Autowired
    public CurrencyConversionService currencyConversionService;

    @Autowired
    public DataFetchingService dataFetchingService;

    public ResponseEntity<Currency> createCurrency(Currency newCurrency) {
        return currencyManagementService.createCurrency(newCurrency);
    }
    public CurrencyAggregatorService() {

    }
    public ResponseEntity<List<String>> getAllCurrencies() {
        return currencyManagementService.getAllCurrencies();
    }

    public ResponseEntity<CurrencyExchangeRate> createExchangeRate(CurrencyExchangeRate newExchangeRate) {
        return exchangeRateManagementService.createExchangeRate(newExchangeRate);
    }

    public ResponseEntity<List<Map<String, Map<String, Float>>>> getAllExchangeRates(){
        return exchangeRateManagementService.getAllExchangeRates();
    }

    public ResponseEntity<List<Map<String, Map<String, Float>>>> getExchangeRatesByDate(String date) {
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