package com.shirkanesi.artemis.client.model.submission;

import com.shirkanesi.artemis.client.model.participation.Participation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemis.client.logic.ArtemisClient;
import com.shirkanesi.artemis.client.model.Result;
import com.shirkanesi.artemis.client.model.error.JHipsterError;
import com.shirkanesi.artemis.client.model.exercise.ExerciseTypes;
import com.squareup.okhttp.Response;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public abstract class Submission {

    @JsonProperty
    private ExerciseTypes submissionExerciseType;

    @JsonProperty
    private int id;

    @JsonProperty
    private boolean submitted;

    @JsonProperty
    private Participation participation;

    @JsonProperty
    private String submissionDate;

    @JsonProperty
    private boolean empty = false;

    @JsonProperty
    private int durationInMinutes;

    @JsonProperty
    private Result[] results;

    /**
     * Locks a submission (starts assessment by the current user) iff the submission is not currently locked or an assessment has been submitted.
     *
     * @param client the {@link ArtemisClient} to use to interact with Artemis
     * @return true iff the submission could be locked. False of not.
     */
    public boolean lockSubmission(ArtemisClient client) {
        if (this.isAssessed()) {
            log.info("Skipped submission {} ({} / {}) [already submitted]", this.getId(), this.getParticipation().getStudent().getParticipantIdentifier(), this.getParticipation().getStudent().getName());
            return false;
        } else if (this.isAssessmentStarted()) {
            log.info("Skipped submission {} ({} / {}) [already started]", this.getId(), this.getParticipation().getStudent().getParticipantIdentifier(), this.getParticipation().getStudent().getName());
            return false;
        }

        try {
            Response response = this.doLockSubmission(client);
            if (response.code() == 200) {
                // everything worked
                log.info("Locked submission {} ({} / {})", this.getId(), this.getParticipation().getStudent().getParticipantIdentifier(), this.getParticipation().getStudent().getName());
                return true;
            } else {
                // error while locking submission (artemis side)
                log.warn("Could not lock this. Cause: {}", JHipsterError.fromJson(response.body().string()).getFormattedMessage());
                return false;
            }
        } catch (IOException e) {
            // general error (client side)
            log.warn("Could not lock submission (client-side) {} ({})", this.getId(), e.getMessage());
            return false;
        }
    }

    protected abstract Response doLockSubmission(ArtemisClient client) throws IOException;

    public boolean cancelAssessmentOfSubmission(ArtemisClient client) {
        try {
            Response response = this.doCancelAssessmentOfSubmission(client);
            if (response.code() == 200) {
                // everything worked
                log.info("Canceled submission {} ({} / {})", this.getId(), this.getParticipation().getStudent().getParticipantIdentifier(), this.getParticipation().getStudent().getName());
                return true;
            } else {
                // error while canceling submission (artemis side)
                log.warn("Could not cancel this. Cause: {}", JHipsterError.fromJson(response.body().string()).getFormattedMessage());
                return false;
            }
        } catch (IOException e) {
            // general error (client side)
            log.warn("Could not cancel submission (client-side) {} ({})", this.getId(), e.getMessage());
            return false;
        }
    }

    protected abstract Response doCancelAssessmentOfSubmission(ArtemisClient client) throws IOException;

    public boolean isAssessmentStarted() {
        if (this.results == null || this.results.length == 0) return false;
        Result latestResult = results.length > 1 ? this.results[results.length - 1] : results[0];
        return !latestResult.getAssessmentType().equals("AUTOMATIC") && latestResult.getCompletionDate() == null;
    }
    public boolean isAssessed() {
        // Stupid way to check, if assessment is finished; only difference between started and finished is the completion-date...
        if (this.results == null || this.results.length == 0) return false;
        Result latestResult = results.length > 1 ? this.results[results.length - 1] : results[0];
        return !latestResult.getAssessmentType().equals("AUTOMATIC") && latestResult.getCompletionDate() != null;
    }

}
