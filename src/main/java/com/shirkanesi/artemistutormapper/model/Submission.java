package com.shirkanesi.artemistutormapper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Submission {

    @JsonProperty
    private String submissionExerciseType;

    @JsonProperty
    private int id;

    @JsonProperty
    private boolean submitted;

    @JsonProperty
    private Participation participation;

    @JsonProperty
    private String submissionDate;

    @JsonProperty
    private String commitHash;

    @JsonProperty
    private boolean buildFailed = false;

    @JsonProperty
    private boolean buildArtifact = false;

    @JsonProperty
    private boolean empty = false;

    @JsonProperty
    private int durationInMinutes;

    @JsonProperty
    private Result[] results;

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
