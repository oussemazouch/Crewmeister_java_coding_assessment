
package com.crewmeister.cmcodingchallenge.currency.dtos;

import java.util.HashMap;
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
    "dimensions"
})
@Generated("jsonschema2pojo")
@Getter
@Setter
public class Structure {

    @JsonProperty("dimensions")
    private Dimension dimension;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


}
