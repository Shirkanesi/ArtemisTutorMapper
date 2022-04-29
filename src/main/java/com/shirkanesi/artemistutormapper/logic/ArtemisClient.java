package com.shirkanesi.artemistutormapper.logic;

import com.shirkanesi.artemistutormapper.model.Submission;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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

    public boolean lockSubmission(Submission submission) {
        String url = String.format(ARTEMIS_BASE_URL + "/api/programming-submissions/%d/lock", submission.getId());

//        System.out.println("Would request: " + url);
//        if (1 > 0) {
//            throw new IllegalStateException("");
//        }
        Request.Builder request = new Request.Builder().get().url(url);
        try {
            Response response = this.makeRequest(request);
            log.info("Locked submission {} ({} / {})", submission.getId(), submission.getParticipation().getStudent().getParticipantIdentifier(), submission.getParticipation().getStudent().getName());
            return response.code() == 200;
        } catch (IOException e) {
            log.warn("Could not lock submission {} ({})", submission.getId(), e.getMessage());
        }
        return false;
    }

    public List<Submission> getSubmissionsForExercise(int exerciseId) {
        String url = String.format(ARTEMIS_BASE_URL + "/api/exercises/%d/programming-submissions?submittedOnly=true", exerciseId);
        Request.Builder request = new Request.Builder().get().url(url);
        try {
            Response response = this.makeRequest(request);
            Submission[] submissions = OBJECT_MAPPER.readValue(response.body().string(), Submission[].class);
            return Arrays.asList(submissions);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not load submissions for exercise %d", exerciseId), e);
//            e.printStackTrace();
        }
    }

}
