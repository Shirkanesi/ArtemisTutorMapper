package com.shirkanesi.artemistutormapper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

    @JsonProperty
    private int id;

    @JsonProperty
    private Date completionDate;

    @JsonProperty
    private boolean successful = false;

    @JsonProperty
    private double score;

    @JsonProperty
    private boolean rated = false;

    @JsonProperty
    private boolean hasFeedback;

    @JsonProperty
    private ArtemisUser assessor;

    @JsonProperty
    private String assessmentType;

    @JsonProperty
    private boolean hasComplaint = false;

}
