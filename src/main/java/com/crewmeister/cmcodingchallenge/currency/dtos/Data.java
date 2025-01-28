
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
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "structure",
    "dataSets"
})
@Generated("jsonschema2pojo")
@Getter
@Setter
public class Data {

    @JsonProperty("structure")
    private Structure structure;
    @JsonProperty("dataSets")
    private List<DataSet> dataSets = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
