package com.crewmeister.cmcodingchallenge.currency.services;

import com.crewmeister.cmcodingchallenge.currency.entities.Currency.Currency;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyManagementService {

    @Autowired
    private CurrencyRepo currencyRepo;

    public ResponseEntity<Currency> createCurrency(Currency newCurrency) {
        try {
            Currency savedCurrency = currencyRepo.save(newCurrency);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCurrency);
        } catch (Exception exception) {
            System.err.println("Error saving currency: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<String>> getAllCurrencies() {
        try {
            List<String> currencies = currencyRepo.findAll().stream().map(Currency::getCurrencyId).collect(Collectors.toList());
            return new ResponseEntity<>(currencies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}