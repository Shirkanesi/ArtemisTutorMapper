package com.shirkanesi.artemistutormapper.logic;

import com.shirkanesi.artemistutormapper.model.Course;
import com.shirkanesi.artemistutormapper.model.Submission;
import com.shirkanesi.artemistutormapper.model.error.JHipsterError;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
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

    /**
     * Locks a submission (starts assessment by the current user) iff the submission is not currently locked or an assessment has been submitted.
     *
     * @param submission the submission to lock
     * @return true iff the submission could be locked. False of not.
     */
    public boolean lockSubmission(Submission submission) {

        if (submission.isAssessed()) {
            log.info("Skipped submission {} ({} / {}) [already submitted]", submission.getId(), submission.getParticipation().getStudent().getParticipantIdentifier(), submission.getParticipation().getStudent().getName());
            return false;
        } else if (submission.isAssessmentStarted()) {
            log.info("Skipped submission {} ({} / {}) [already started]", submission.getId(), submission.getParticipation().getStudent().getParticipantIdentifier(), submission.getParticipation().getStudent().getName());
            return false;
        }

        String url = String.format(ARTEMIS_BASE_URL + "/api/programming-submissions/%d/lock", submission.getId());
        Request.Builder request = new Request.Builder().get().url(url);

        try {
            Response response = this.makeRequest(request);
            if (response.code() == 200) {
                // everything worked
                log.info("Locked submission {} ({} / {})", submission.getId(), submission.getParticipation().getStudent().getParticipantIdentifier(), submission.getParticipation().getStudent().getName());
                return true;
            } else {
                // error while locking submission (artemis side)
                log.warn("Could not lock submission. Cause: {}", JHipsterError.fromJson(response.body().string()).getFormattedMessage());
                return false;
            }
        } catch (IOException e) {
            // general error (client side)
            log.warn("Could not lock submission (client-side) {} ({})", submission.getId(), e.getMessage());
            return false;
        }
    }

    public boolean cancelAssessmentOfSubmission(Submission submission) {
        String url = String.format(ARTEMIS_BASE_URL + "/api/programming-submissions/%d/cancel-assessment", submission.getId());

        Request.Builder request = new Request.Builder().put(RequestBody.create(null, new byte[0])).url(url);

        try {
            Response response = this.makeRequest(request);
            if (response.code() == 200) {
                // everything worked
                log.info("Canceled submission {} ({} / {})", submission.getId(), submission.getParticipation().getStudent().getParticipantIdentifier(), submission.getParticipation().getStudent().getName());
                return true;
            } else {
                // error while canceling submission (artemis side)
                log.warn("Could not cancel submission. Cause: {}", JHipsterError.fromJson(response.body().string()).getFormattedMessage());
                return false;
            }
        } catch (IOException e) {
            // general error (client side)
            log.warn("Could not cancel submission (client-side) {} ({})", submission.getId(), e.getMessage());
            return false;
        }
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
        }
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
