package com.shirkanesi.artemis.client.model.repository;

import com.shirkanesi.artemis.client.exception.RepositoryOperationException;
import com.shirkanesi.artemis.client.logic.ArtemisClient;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;

public class GitLabArtemisRepository implements IArtemisRepository {

    private Project project;

    @Override
    public RepositoryType getRepositoryType() {
        return RepositoryType.GITLAB;
    }

    @Override
    public boolean lockRepository(ArtemisClient artemisClient) {
        // TODO
        return false;
    }

    @Override
    public boolean unlockRepository(ArtemisClient artemisClient) {
        try {
            // TODO
            artemisClient.getGitLabApi().getProtectedBranchesApi().unprotectBranch(this.project, "main");
            return true;
        } catch (GitLabApiException e) {
            throw new RepositoryOperationException(e);
        }
    }
}
