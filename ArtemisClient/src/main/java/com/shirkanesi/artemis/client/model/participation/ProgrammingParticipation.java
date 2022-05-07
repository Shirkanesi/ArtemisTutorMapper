package com.shirkanesi.artemis.client.model.participation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemis.client.logic.ArtemisClient;
import com.shirkanesi.artemis.client.logic.repository.GitLabArtemisRepository;
import com.shirkanesi.artemis.client.logic.repository.IArtemisRepository;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.Project;

import java.net.URL;

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

    public IArtemisRepository getArtemisRepository(ArtemisClient artemisClient) {
        // Switch over the value in ArtemisClient. Ugly but assumes Artemis only uses one repository-type at once.
        // Switch instead of if to allow easy expansion in the future.
        return switch (artemisClient.getRepositoryType()) {
            case GITLAB -> new GitLabArtemisRepository(artemisClient.getGitLabApi(), this.getUserIndependentRepositoryUrl());

            // In case it is none of the above types --> fail!
            default -> throw new UnsupportedOperationException("This repository-type has not been implemented yet!");
        };
    }

}
