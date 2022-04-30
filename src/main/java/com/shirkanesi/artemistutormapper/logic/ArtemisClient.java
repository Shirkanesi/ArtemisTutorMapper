package com.shirkanesi.artemistutormapper.logic;

import com.shirkanesi.artemistutormapper.model.Course;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.shirkanesi.artemistutormapper.ArtemisTutorMapper.ARTEMIS_BASE_URL;
import static com.shirkanesi.artemistutormapper.ArtemisTutorMapper.OBJECT_MAPPER;

@Slf4j
public class ArtemisClient {

    private final AuthenticationService authenticationService;

    private final OkHttpClient httpClient;

    public ArtemisClient(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.httpClient = new OkHttpClient();
    }

    public Response makeRequest(Request.Builder requestBuilder) throws IOException {
        Request request = requestBuilder.addHeader("Authorization", "Bearer " + this.authenticationService.getToken()).build();
        Call call = this.httpClient.newCall(request);
        return call.execute();
    }

    public List<Course> getCoursesForManagement() {
        String url = ARTEMIS_BASE_URL + "/api/courses/exercises-for-management-overview?onlyActive=true";
        Request.Builder request = new Request.Builder().get().url(url);
        try {
            Response response = this.makeRequest(request);
            Course[] courses = OBJECT_MAPPER.readValue(response.body().string(), Course[].class);
            return Arrays.asList(courses);
        } catch (IOException e) {
            throw new RuntimeException("Could not load courses from Artemis", e);
        }
    }

}
