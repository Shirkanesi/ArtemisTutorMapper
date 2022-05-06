package com.shirkanesi.artemis.client.logic;

import com.shirkanesi.artemis.client.exception.ArtemisClientException;
import com.shirkanesi.artemis.client.logic.repository.RepositoryType;
import com.shirkanesi.artemis.client.model.Course;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ArtemisClient {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper() {{
        enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
    }};

    private final AuthenticationService authenticationService;

    private final OkHttpClient httpClient;

    @Getter
    private RepositoryType repositoryType = RepositoryType.GITLAB;

    private GitLabApi gitLabApi;

    /**
     * Creates a new {@link ArtemisClient} using the supplied {@link AuthenticationService}
     * @param authenticationService the {@link AuthenticationService} to use for Artemis-authentication
     */
    public ArtemisClient(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.httpClient = new OkHttpClient();
    }

    /**
     * Executes the {@link Request} defined by the passed {@link Request.Builder}.
     * An Artemis-authorization-header will be added to the request
     * @param requestBuilder a {@link Request.Builder} the request to make is defined in
     * @return the {@link Response} of the request
     * @throws IOException thrown if request fails
     */
    public Response makeRequest(Request.Builder requestBuilder) throws IOException {
        Request request = requestBuilder.addHeader("Authorization", "Bearer " + this.authenticationService.getToken()).build();
        Call call = this.httpClient.newCall(request);
        return call.execute();
    }

    /**
     * Loads all currently active courses from Artemis (where the user has management-permissions for) // TODO: check if this is true
     * @return a {@link List} containing all loaded courses
     * @throws ArtemisClientException thrown, if the courses can not be loaded from Artemis or the response is invalid.
     */
    public List<Course> getCoursesForManagement() {
        return this.getCoursesForManagement(true);
    }

    /**
     * Loads all courses from Artemis (where the user has management-permissions for) // TODO: check if this is true
     * @param onlyActive if true, only active courses will be loaded, if false, old courses will be loaded as well.
     * @return a {@link List} containing all loaded courses
     * @throws ArtemisClientException thrown, if the courses can not be loaded from Artemis or the response is invalid.
     */
    public List<Course> getCoursesForManagement(boolean onlyActive) throws ArtemisClientException {
        String url = this.getArtemisBaseUrl() + String.format("/api/courses/exercises-for-management-overview?onlyActive=%b", onlyActive);
        Request.Builder request = new Request.Builder().get().url(url);
        try {
            Response response = this.makeRequest(request);
            Course[] courses = OBJECT_MAPPER.readValue(response.body().string(), Course[].class);
            return Arrays.asList(courses);
        } catch (IOException e) {
            throw new ArtemisClientException("Could not load courses from Artemis", e);
        }
    }

    public String getArtemisBaseUrl() {
        return this.authenticationService.getArtemisUrl();
    }

    public GitLabApi getGitLabApi() {
        if (this.gitLabApi == null) {
            // Temporary...
            this.gitLabApi = new GitLabApi(System.getenv("GIT_URL"), System.getenv("GIT_PASSWORD"));
        }
        return gitLabApi;
    }
}
