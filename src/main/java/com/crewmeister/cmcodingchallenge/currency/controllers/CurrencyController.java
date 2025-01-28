package com.crewmeister.cmcodingchallenge.currency.controllers;
import com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos.CurrencyConversionData;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRate;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyRepo;
import com.crewmeister.cmcodingchallenge.currency.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;
    @GetMapping("/init")
    public String initDB(@RequestParam(required = false) String startDate){
       return this.currencyService.initializeDB(startDate);
    }
    @Autowired
    CurrencyRepo currencyRepo;
    @GetMapping("/currencies/")
    public ResponseEntity<List<String>> getCurrencies() {
        return this.currencyService.getAllCurrencies();
    }

    @GetMapping("/currencies/exchange_rates")
    public ResponseEntity<List<CurrencyExchangeRate>> getExchangeRates() {
        return this.currencyService.getAllExchangeRates();
    }


    @GetMapping("/currencies/exchange_rates/find-by-date")
    public ResponseEntity<List<CurrencyExchangeRate>> getExchangeRatesByDate(@RequestParam String date) {
        return this.currencyService.getExchangeRatesByDate(date);
    }

    @PostMapping("/currencies/convert")
    public ResponseEntity<String> convertToEuro(@RequestBody CurrencyConversionData conversionData){
        return this.currencyService.convertToEuro(conversionData);
    }

}
