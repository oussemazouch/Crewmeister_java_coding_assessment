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

    public ResponseEntity<CurrencyExchangeRate> createExchangeRate(CurrencyExchangeRate newExchangeRate) {
        try {
            CurrencyExchangeRate savedExchangeRate = currencyExchangeRepo.save(newExchangeRate);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExchangeRate);
        } catch (Exception exception) {
            System.err.println("Error saving exchange rate: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<CurrencyExchangeRate>> getAllExchangeRates() {
        try {
            List<CurrencyExchangeRate> exchangeRates = currencyExchangeRepo.findAll();
            return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    // Optimize the formatResult function
    public List<Map<String, Map<String, Float>>> formatResult(List<CurrencyExchangeRate> input) {
        // Fetch all currency IDs from the repository
        List<String> currencies = currencyRepo.findAll()
                .stream()
                .map(Currency::getCurrencyId)
                .collect(Collectors.toList());

        // Pre-format the input data into a map for easier lookup
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

        // Build the final result
        Map<String, Map<String, Float>> currencyDataMap = currencies.stream()
                .collect(Collectors.toMap(
                        currency -> currency,
                        currency -> formattedInput.getOrDefault(currency, new TreeMap<>()),
                        (oldValue, newValue) -> newValue,
                        TreeMap::new
                ));

        return Collections.singletonList(currencyDataMap);
    }

    // Optimize the formatToMap function
    public Map<String, Map<String, Float>> formatToMap(CurrencyExchangeRate currencyExchangeRate) {
        String currencyId = currencyExchangeRate.getCurrencyExchangeRateId().getCurrency().getCurrencyId();
        String date = currencyExchangeRate.getCurrencyExchangeRateId().getDate();
        Float rate = currencyExchangeRate.getExchangeRate();

        return Map.of(currencyId, Map.of(date, rate));
    }



    public ResponseEntity<List<CurrencyExchangeRate>> getExchangeRatesByDate(String date) {
        try {
            List<CurrencyExchangeRate> exchangeRates = currencyExchangeRepo.findByCurrencyExchangeRateIdDate(date);
            return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}