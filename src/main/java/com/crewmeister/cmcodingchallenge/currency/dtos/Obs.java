
package com.crewmeister.cmcodingchallenge.currency.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "keyPosition",
    "values"
})
@Generated("jsonschema2pojo")
@Getter
@Setter
public class Obs {

    @JsonProperty("id")
    private String id;
    @JsonProperty("keyPosition")
    private Integer keyPosition;
    @JsonProperty("values")
    private List<Date> values = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
