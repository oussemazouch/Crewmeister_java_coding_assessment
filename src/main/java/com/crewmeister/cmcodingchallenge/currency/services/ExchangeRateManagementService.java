package com.crewmeister.cmcodingchallenge.currency.services;

import com.crewmeister.cmcodingchallenge.currency.entities.Currency.Currency;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRate;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyExchangeRateRepo;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ExchangeRateManagementService {
    @Autowired
    private CurrencyRepo currencyRepo;
    @Autowired
    private CurrencyExchangeRateRepo currencyExchangeRepo;

    // Creates and saves a new exchange rate to the repository.
    public ResponseEntity<CurrencyExchangeRate> createExchangeRate(CurrencyExchangeRate newExchangeRate) {
        try {
            CurrencyExchangeRate savedExchangeRate = currencyExchangeRepo.save(newExchangeRate);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExchangeRate);
        } catch (Exception exception) {
            System.err.println("Error saving exchange rate: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ExchangeRateManagementService(CurrencyRepo currencyRepo, CurrencyExchangeRateRepo currencyExchangeRepo) {
        this.currencyRepo = currencyRepo;
        this.currencyExchangeRepo = currencyExchangeRepo;
    }
    // Fetches all exchange rates from the database and returns them in a formatted response.
    public ResponseEntity<List<Map<String, Map<String, Float>>>> getAllExchangeRates(){
        try {
            List<CurrencyExchangeRate> exchangeRates = currencyExchangeRepo.findAll();
            List<Map<String, Map<String, Float>>> formattedResult = formatResult(exchangeRates);
            return new ResponseEntity<>(formattedResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // Formats the exchange rates into a structured map of currency IDs, dates, and rates.
    public List<Map<String, Map<String, Float>>> formatResult(List<CurrencyExchangeRate> input) {
        List<String> currencies = currencyRepo.findAll()
                .stream()
                .map(Currency::getCurrencyId)
                .collect(Collectors.toList());

        Map<String, Map<String, Float>> formattedInput = input.stream()
                .collect(Collectors.groupingBy(
                        er -> er.getCurrencyExchangeRateId().getCurrency().getCurrencyId(),
                        TreeMap::new,
                        Collectors.toMap(
                                er -> er.getCurrencyExchangeRateId().getDate(),
                                CurrencyExchangeRate::getExchangeRate,
                                (oldValue, newValue) -> newValue,
                                TreeMap::new
                        )
                ));

        Map<String, Map<String, Float>> currencyDataMap = currencies.stream()
                .collect(Collectors.toMap(
                        currency -> currency,
                        currency -> formattedInput.getOrDefault(currency, new TreeMap<>()),
                        (oldValue, newValue) -> newValue,
                        TreeMap::new
                ));

        return Collections.singletonList(currencyDataMap);
    }

    // Fetches exchange rates by a specific date and returns them in a formatted response.
    public ResponseEntity<List<Map<String, Map<String, Float>>>> getExchangeRatesByDate(String date){
        try {
            List<CurrencyExchangeRate> exchangeRates = currencyExchangeRepo.findByCurrencyExchangeRateIdDate(date);
            List<Map<String, Map<String, Float>>> formattedResult = formatResult(exchangeRates);

            return new ResponseEntity<>(formattedResult, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}