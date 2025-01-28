
package com.crewmeister.cmcodingchallenge.currency.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "action",
    "validFrom",
    "series",
    "links"
})
@Generated("jsonschema2pojo")
@Getter
@Setter
public class DataSet {

    @JsonProperty("action")
    private String action;
    @JsonProperty("validFrom")
    private String validFrom;
    @JsonProperty("series")
    private JsonNode series;
    @JsonProperty("links")
    private List<Object> links = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
