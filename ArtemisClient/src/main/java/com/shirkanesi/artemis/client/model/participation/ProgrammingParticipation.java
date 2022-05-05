package com.shirkanesi.artemis.client.model.participation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ProgrammingParticipation extends Participation {

    @JsonProperty
    private String repositoryUrl;

    @JsonProperty
    private String buildPlanId;

    @JsonProperty
    private String userIndependentRepositoryUrl;

}
