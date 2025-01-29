package com.crewmeister.cmcodingchallenge.currency;

import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyExchangeRateRepo;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyRepo;
import com.crewmeister.cmcodingchallenge.currency.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurrencyAggregatorServiceTest{

    @Mock
    private CurrencyRepo currencyRepo;

    @Mock
    CurrencyExchangeRateRepo exchangeRateRepo;
    @Mock
    private CurrencyManagementService currencyManagementService;

    @Mock
    private ExchangeRateManagementService exchangeRateManagementService;

    @Mock
    private CurrencyConversionService currencyConversionService;

    @Mock
    private DataFetchingService dataFetchingService;



    private CurrencyAggregatorService currencyService;
    @BeforeEach
    void setUp(){
        this.currencyService = new CurrencyAggregatorService();
        // Instantiate dependent services
        this.currencyConversionService = new CurrencyConversionService(exchangeRateRepo);
        this.exchangeRateManagementService = new ExchangeRateManagementService(currencyRepo, exchangeRateRepo);
        this.currencyManagementService = new CurrencyManagementService(currencyRepo);
        currencyService.currencyManagementService = currencyManagementService;
        currencyService.exchangeRateManagementService = exchangeRateManagementService;
        currencyService.currencyConversionService = currencyConversionService;
        currencyService.dataFetchingService = dataFetchingService;
    }



    @Test
    void canGetAllCurrencies() {
        currencyService.getAllCurrencies();
        verify(currencyRepo).findAll();
    }

    @Test
    void canGetAllExchangeRates() {
        currencyService.getAllExchangeRates();
        verify(exchangeRateRepo).findAll();
    }

    @Test
    void canGetExchangeRatesByDate() {
        currencyService.getExchangeRatesByDate("2025-01-01");
        verify(exchangeRateRepo).findByCurrencyExchangeRateIdDate("2025-01-01");
    }

}