package com.shirkanesi.artemis.client.model.exercise;

import com.shirkanesi.artemis.client.model.deserialize.ExerciseDeserializer;
import com.shirkanesi.artemis.client.model.submission.Submission;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shirkanesi.artemis.client.logic.ArtemisClient;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@ToString
@JsonDeserialize(using = ExerciseDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Exercise {

    @JsonProperty
    private ExerciseTypes type;

    @JsonProperty
    private int id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String shortName;

    @JsonProperty
    private int maxPoints;

    @JsonProperty
    private int bonusPoints;

    @JsonProperty
    private String assessmentType;

    @JsonProperty
    private Date dueDate;

    @JsonProperty
    private String mode;

    @JsonProperty
    private boolean allowComplaintsForAutomaticAssessments;

    @JsonProperty
    private String includedInOverallScore;

    @JsonProperty
    private String problemStatement;

    @JsonProperty
    private boolean presentationScoreEnabled;

    @JsonProperty
    private boolean secondCorrectionEnabled;

    @JsonProperty
    private String testRepositoryUrl;

    @JsonProperty
    private boolean publishBuildPlanUrl;

    @JsonProperty
    private boolean allowOnlineEditor;

    @JsonProperty
    private boolean allowOfflineIde;

    @JsonProperty
    private boolean staticCodeAnalysisEnabled;

    @JsonProperty
    private int maxStaticCodeAnalysisPenalty;

    @JsonProperty
    private String programmingLanguage;

    @JsonProperty
    private String packageName;

    @JsonProperty
    private boolean sequentialTestRuns;

    @JsonProperty
    private boolean showTestNamesToStudents;

    @JsonProperty
    private boolean testCasesChanged;

    @JsonProperty
    private String projectKey;

    @JsonProperty
    private String projectType;

    @JsonProperty
    private boolean testwiseCoverageEnabled;

    @JsonProperty
    private boolean checkoutSolutionRepository;

    @JsonProperty
    private boolean isLocalSimulation;

    @JsonProperty
    private String exerciseType;

    @JsonProperty
    private boolean studentAssignedTeamIdComputed;

    @JsonProperty
    private boolean gradingInstructionFeedbackUsed;

    @JsonProperty
    private boolean released;

    @JsonProperty
    private boolean exampleSolutionPublished;

    @JsonProperty
    private boolean teamMode;

    @JsonProperty
    private boolean visibleToStudents;

    public abstract List<? extends Submission> getSubmissionsForExercise(ArtemisClient client);

}
