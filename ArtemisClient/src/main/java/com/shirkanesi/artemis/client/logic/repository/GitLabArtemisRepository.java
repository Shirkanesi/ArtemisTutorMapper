package com.shirkanesi.artemis.client.logic.repository;

import com.shirkanesi.artemis.client.exception.RepositoryOperationException;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;

import java.io.IOException;
import java.net.URL;

@Slf4j
public class GitLabArtemisRepository implements IArtemisRepository {

    private final GitLabApi gitLabApi;

    private final Project project;

    public GitLabArtemisRepository(GitLabApi gitLabApi, String repositoryUrlString) {
        this.gitLabApi = gitLabApi;

        try {
            URL repositoryUrl = new URL(repositoryUrlString);
            String[] split = repositoryUrl.getPath().split("/");
            this.project = gitLabApi.getProjectApi().getProject(split[1], split[2].replaceAll("\\.git", ""));
        } catch (IOException | GitLabApiException e) {
            throw new RepositoryOperationException("Could not load GitLab-Repository", e);
        }

        log.debug("Initialized GitLabArtemisRepository for {}", project.getPathWithNamespace());
    }

    public GitLabArtemisRepository(GitLabApi gitLabApi, Project project) {
        this.gitLabApi = gitLabApi;
        this.project = project;
        log.debug("Initialized GitLabArtemisRepository for {}", project.getPathWithNamespace());
    }

    @Override
    public RepositoryType getRepositoryType() {
        return RepositoryType.GITLAB;
    }

    @Override
    public boolean protectMainBranch() {
        // TODO
        return false;
    }

    @Override
    public boolean unprotectMainBranch() {
        try {
            // TODO
            if (this.isMainBranchProtected()) {
                this.gitLabApi.getProtectedBranchesApi().unprotectBranch(this.project, DEFAULT_BRANCH_NAME);
            }
            return true;
        } catch (GitLabApiException e) {
            log.warn("Could not unprotect " + DEFAULT_BRANCH_NAME + "-branch!", e);
            throw new RepositoryOperationException(e);
        }
    }

    @Override
    public boolean isMainBranchProtected() {
        try {
            this.gitLabApi.getProtectedBranchesApi().getProtectedBranch(this.project, DEFAULT_BRANCH_NAME);
            return true;
        } catch (GitLabApiException e) {
            return false;
        }
    }

    public Project getGitLabProject() {
        return this.project;
    }
}
