package com.crewmeister.cmcodingchallenge.currency.services;

import com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos.CurrencyData;
import com.crewmeister.cmcodingchallenge.currency.dtos.Date;
import com.crewmeister.cmcodingchallenge.currency.dtos.Response;
import com.crewmeister.cmcodingchallenge.currency.entities.Currency.Currency;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRate;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRateId;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyExchangeRateRepo;
import com.crewmeister.cmcodingchallenge.currency.repositories.CurrencyRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

import static com.crewmeister.cmcodingchallenge.currency.utils.utils.roundToTwoDecimals;
import static com.crewmeister.cmcodingchallenge.currency.utils.utils.withBigDecimal;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepo currencyRepo;
    @Autowired
    CurrencyExchangeRateRepo currencyExchangeRepo;

    @Autowired
    private WebClient webClient;
    public WebClient.ResponseSpec getData(String URI){
       return webClient.get().uri(URI).retrieve();
    }
    public Response getAllData(String startDate){
        StringBuilder uriBuilder = new StringBuilder("/data/BBEX3/D..EUR.BB.AC.000?detail=dataonly");
        if (startDate != null) {
            uriBuilder.append("&startPeriod=").append(startDate);
        }
        return getData(uriBuilder.toString()).bodyToMono(Response.class).block();
    }

    public List<CurrencyData> extractAllCurrencies(Response responseData) {
        return responseData
                .getData()
                .getStructure()
                .getDimensions()
                .getCurrencies()
                .get(1)
                .getValues();
    }

    public List<Date> extractAllDates(Response responseData) {
        return responseData
                .getData()
                .getStructure()
                .getDimensions()
                .getObservation()
                .get(0)
                .getValues();
    }

    public Map<String, JsonNode> extractAllExchangeRates(Response responseData) {
        JsonNode seriesNode = responseData
                .getData()
                .getDataSets()
                .get(0)
                .getSeries();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(seriesNode, new TypeReference<Map<String, JsonNode>>() {});
    }
    public String initializeDB(String startDate){
        Response allData = this.getAllData(startDate);
        List<CurrencyData> currencies = this.extractAllCurrencies(allData);
        currencies.stream().map(CurrencyData::getId).forEach(value -> addCurrency (new Currency(value)));
        initializeExchangeRateTab(allData);
        return "DB initialized successfully!";
    }
    public void initializeExchangeRateTab(Response data) {
        List<CurrencyData> currencies = extractAllCurrencies(data);
        List<Date> dates = extractAllDates(data);
        Map<String, JsonNode> exchangeRates = extractAllExchangeRates(data);

        ObjectMapper objectMapper = new ObjectMapper();

        int currencyIndex = 0;
        for (JsonNode seriesNode : exchangeRates.values()) {
            JsonNode observationsNode = seriesNode.get("observations");
            if (observationsNode == null) {
                currencyIndex++;
                continue;
            }

            Map<String, List<Float>> currencyRates = objectMapper.convertValue(
                    observationsNode, new TypeReference<Map<String, List<Float>>>() {}
            );

            CurrencyExchangeRateId exchangeRateId = new CurrencyExchangeRateId();
            exchangeRateId.setCurrency(new Currency(currencies.get(currencyIndex).getId()));

            int dateIndex = 0;
            for (List<Float> rateValues : currencyRates.values()) {
                if (rateValues.isEmpty() || rateValues.get(0) == null) {
                    dateIndex++;
                    continue;
                }
                float exchangeRateValue = roundToTwoDecimals(rateValues.get(0));
                CurrencyExchangeRate exchangeRate = new CurrencyExchangeRate();
                exchangeRateId.setDate(dates.get(dateIndex).getId());
                exchangeRate.setCurrencyExchangeRateId(exchangeRateId);
                exchangeRate.setExchange_rate(exchangeRateValue);
                addExchangeRate(exchangeRate);
                dateIndex++;
            }
            currencyIndex++;
        }
    }

    public ResponseEntity<Currency> addCurrency(Currency newCurrency) {
        try {
            Currency savedCurrency = currencyRepo.save(newCurrency);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCurrency);
        } catch (Exception exception) {
            System.err.println("Error saving currency: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<CurrencyExchangeRate> addExchangeRate(CurrencyExchangeRate newExchangeRate) {
        try {
            CurrencyExchangeRate savedExchangeRate = currencyExchangeRepo.save(newExchangeRate);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedExchangeRate);
        } catch (Exception exception) {
            System.err.println("Error saving exchange rate: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
