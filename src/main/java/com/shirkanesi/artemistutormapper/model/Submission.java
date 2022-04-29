package com.shirkanesi.artemistutormapper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Submission {

    @JsonProperty
    private String submissionExerciseType;

    @JsonProperty
    private int id;

    @JsonProperty
    private boolean submitted;

    @JsonProperty
    private Participation participation;

}
