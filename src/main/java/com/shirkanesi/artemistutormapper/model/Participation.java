package com.shirkanesi.artemistutormapper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Participation {

    @JsonProperty
    private String type;

    @JsonProperty
    private int id;

    @JsonProperty
    private String initializationState;

    @JsonProperty
    private Date initializationDate;

    @JsonProperty
    private boolean testRun = false;

    @JsonProperty
    private int submissionCount;

    @JsonProperty
    private ArtemisUser student;

    @JsonProperty
    private String repositoryUrl;

    @JsonProperty
    private String buildPlanId;

    @JsonProperty
    private String userIndependentRepositoryUrl;

    @JsonProperty
    private String participantName;

    @JsonProperty
    private String participantIdentifier;

}
