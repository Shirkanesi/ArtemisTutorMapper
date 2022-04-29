package com.shirkanesi.artemistutormapper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Participation {

    @JsonProperty
    private String type;

    @JsonProperty
    private int id;

    @JsonProperty
    private String repositoryUrl;

    @JsonProperty
    private Student student;

}
