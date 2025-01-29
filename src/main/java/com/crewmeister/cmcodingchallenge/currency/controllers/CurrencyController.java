package com.crewmeister.cmcodingchallenge.currency.controllers;
import com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos.CurrencyConversionData;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyRepo;
import com.crewmeister.cmcodingchallenge.currency.services.CurrencyAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api")
public class CurrencyController {
    @Autowired
    CurrencyAggregatorService currencyService;
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

    @GetMapping("/currencies/exchange-rates")
    public ResponseEntity<List<Map<String, Map<String, Float>>>> getExchangeRates() {
        return this.currencyService.getAllExchangeRates();
    }


    @GetMapping("/currencies/exchange-rates/find-by-date")
    public ResponseEntity<List<Map<String, Map<String, Float>>>> getExchangeRatesByDate(@RequestParam String date) {
        return this.currencyService.getExchangeRatesByDate(date);
    }

    @PostMapping("/currencies/convert")
    public ResponseEntity<String> convertToEuro(@RequestBody CurrencyConversionData conversionData){
        return this.currencyService.convertToEuro(conversionData);
    }

}
