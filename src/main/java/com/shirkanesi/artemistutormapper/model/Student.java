package com.shirkanesi.artemistutormapper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

    @JsonProperty
    private int id;

    @JsonProperty
    private String login;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String email;

    @JsonProperty
    private boolean activated;

    @JsonProperty
    private String langKey;

    @JsonProperty
    private String name;

    @JsonProperty
    private String participantIdentifier;

}
