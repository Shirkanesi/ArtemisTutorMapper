package com.shirkanesi.artemis.client.model.participation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemis.client.logic.ArtemisClient;
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

    @SneakyThrows
    public Project getGitlabProject(ArtemisClient artemisClient) {
        GitLabApi gitLabApi = artemisClient.getGitLabApi();
        System.out.println(this.userIndependentRepositoryUrl);

        URL repositoryUrl = new URL(this.userIndependentRepositoryUrl);
        String[] split = repositoryUrl.getPath().split("/");
        Project project = gitLabApi.getProjectApi().getProject(split[1], split[2].replaceAll("\\.git", ""));
        System.out.println(project);
        return project;
    }

}
