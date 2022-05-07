package com.shirkanesi.artemis.client.logic.repository;

public interface IArtemisRepository {

    /**
     * The name of the default branch in the repositories. Currently, Artemis uses "main"
     */
    String DEFAULT_BRANCH_NAME = "main";

    /**
     * Gets the type of the underlying git-server (e.g. GitLab)
     * @return the type of the server
     */
    RepositoryType getRepositoryType();

    /**
     * Protects the main-branch of the git-repository (iff branch-protection is implemented on the git-server)
     * If no branch-protection is implemented, this method will always return false (as the branch was not protected)
     * @return true on success, false otherwise
     */
    boolean protectMainBranch();

    /**
     * Unprotects the main-branch of the git-repository (iff branch-protection is implemented on the git-server)
     * If no branch-protection is implemented, this method will always return true
     *
     * @return true on success, false otherwise
     */
    boolean unprotectMainBranch();

    /**
     * Checks if the main-branch of the repository is protected (iff branch-protection is implemented on the git-server)
     * If no branch-protection is implemented, this method will always return false.
     *
     * @return true, if the main-branch is protected, false if not
     */
    boolean isMainBranchProtected();

}
