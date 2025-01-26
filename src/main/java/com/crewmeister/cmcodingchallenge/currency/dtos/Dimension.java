package com.crewmeister.cmcodingchallenge.currency.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

import com.crewmeister.cmcodingchallenge.currency.dtos.currenciesDtos.Currencies;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "series",
        "observation"
})
@Generated("jsonschema2pojo")
public class Dimension {

    @JsonProperty("series")
    private List<Currencies> currencies = null;
    @JsonProperty("observation")
    private List<Obs> observation = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("series")
    public List<Currencies> getCurrencies() {
        return currencies;
    }

    @JsonProperty("observation")
    public List<Obs> getObservation() {
        return observation;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }


}
