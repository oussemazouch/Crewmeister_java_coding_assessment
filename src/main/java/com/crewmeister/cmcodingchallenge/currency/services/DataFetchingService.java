package com.crewmeister.cmcodingchallenge.currency.services;

import com.crewmeister.cmcodingchallenge.currency.dtos.Date;
import com.crewmeister.cmcodingchallenge.currency.dtos.Response;
import com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos.CurrencyData;
import com.crewmeister.cmcodingchallenge.currency.entities.Currency.Currency;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRate;
import com.crewmeister.cmcodingchallenge.currency.entities.CurrencyExchangeRate.CurrencyExchangeRateId;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.crewmeister.cmcodingchallenge.currency.constants.Constants.DATA_ENDPOINT;
import static com.crewmeister.cmcodingchallenge.currency.utils.utils.roundToTwoDecimals;

@Service
public class DataFetchingService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private CurrencyManagementService currencyManagementService;

    @Autowired
    private ExchangeRateManagementService exchangeRateManagementService;

    public WebClient.ResponseSpec getData(String URI) {
        return webClient.get().uri(URI).retrieve();
    }

    public Response getAllData(String startDate) {
        StringBuilder uriBuilder = new StringBuilder(DATA_ENDPOINT + "?detail=dataonly");
        if (startDate != null) {
            uriBuilder.append("&startPeriod=").append(startDate);
        }
        return getData(uriBuilder.toString()).bodyToMono(Response.class).block();
    }

    public void getLatestData() {
        String now = LocalDate.now().toString();
        this.getData(DATA_ENDPOINT + "?startPeriod=" + now + "&endPeriod=" + now + "&detail=dataonly")
                .bodyToMono(Response.class).block();
    }

    public void updateDB() {
        this.getLatestData();
    }

    public List<CurrencyData> extractAllCurrencies(Response responseData) {
        return responseData
                .getData()
                .getStructure()
                .getDimension()
                .getCurrencies()
                .get(1)
                .getValues();
    }

    public List<Date> extractAllDates(Response responseData) {
        return responseData
                .getData()
                .getStructure()
                .getDimension()
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
        return objectMapper.convertValue(seriesNode, new TypeReference<Map<String, JsonNode>>() {
        });
    }

    // initializes database with data starting from a given starting date.
    public String initializeDB(String startDate) {
        Response allData = this.getAllData(startDate);
        List<CurrencyData> currencies = this.extractAllCurrencies(allData);
        currencies.stream().map(CurrencyData::getId).forEach(value -> currencyManagementService.createCurrency(new Currency(value)));
        populateExchangeRateTab(allData);
        return "DB initialized successfully!";
    }

    // Populates the exchange rate table with data from the response.
    public void populateExchangeRateTab(Response data) {
        List<CurrencyData> currencies = extractAllCurrencies(data);
        List<Date> dates = extractAllDates(data);
        Map<String, JsonNode> exchangeRates = extractAllExchangeRates(data);

        processExchangeRates(currencies, dates, exchangeRates);
    }

    // Processes and saves exchange rates for each currency based on the extracted data
    private void processExchangeRates(List<CurrencyData> currencies, List<Date> dates, Map<String, JsonNode> exchangeRates) {
        ObjectMapper objectMapper = new ObjectMapper();
        int currencyIndex = 0;
        for (JsonNode seriesNode : exchangeRates.values()) {
            JsonNode observationsNode = seriesNode.get("observations");
            if (observationsNode == null) {
                currencyIndex++;
                continue;
            }

            Map<String, List<Float>> currencyRates = objectMapper.convertValue(
                    observationsNode, new TypeReference<Map<String, List<Float>>>() {
                    }
            );

            CurrencyExchangeRateId exchangeRateId = new CurrencyExchangeRateId();
            exchangeRateId.setCurrency(new Currency(currencies.get(currencyIndex).getId()));

            saveExchangeRatesForCurrency(currencyRates, exchangeRateId, dates);
            currencyIndex++;
        }
    }

    // Saves the exchange rates for a given currency and its associated dates
    private void saveExchangeRatesForCurrency(Map<String, List<Float>> currencyRates, CurrencyExchangeRateId exchangeRateId, List<Date> dates) {
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
            exchangeRate.setExchangeRate(exchangeRateValue);
            exchangeRateManagementService.createExchangeRate(exchangeRate);
            dateIndex++;
        }
    }
}