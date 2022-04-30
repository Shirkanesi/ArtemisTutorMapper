package com.shirkanesi.artemistutormapper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemistutormapper.model.exercise.Exercise;
import com.shirkanesi.artemistutormapper.model.exercise.ExerciseTypes;
import com.shirkanesi.artemistutormapper.model.exercise.FileUploadExercise;
import com.shirkanesi.artemistutormapper.model.exercise.ProgrammingExercise;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {


    @JsonProperty
    private int id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String shortName;

    @JsonProperty
    private String studentGroupName;

    @JsonProperty
    private String teachingAssistantGroupName;

    @JsonProperty
    private String editorGroupName;

    @JsonProperty
    private String instructorGroupName;

    @JsonProperty
    private boolean testCourse;

    @JsonProperty
    private boolean onlineCourse;

    @JsonProperty
    private int maxComplaints;

    @JsonProperty
    private int maxTeamComplaints;

    @JsonProperty
    private int maxComplaintTimeDays;

    @JsonProperty
    private int maxComplaintTextLimit;

    @JsonProperty
    private int maxComplaintResponseTextLimit;

    @JsonProperty
    private boolean postsEnabled;

    @JsonProperty
    private int maxRequestMoreFeedbackTimeDays;

    @JsonProperty
    private boolean registrationEnabled;

    @JsonProperty
    private int presentationScore;

    @JsonProperty
    private int accuracyOfScores;

    @JsonProperty
    private List<Exercise> exercises;

    @JsonProperty
    private boolean complaintsEnabled;

    @JsonProperty
    private boolean requestMoreFeedbackEnabled;

    public List<Exercise> getExercises() {
        return Collections.unmodifiableList(this.exercises);
    }

    public Collection<ProgrammingExercise> getProgrammingExercises() {
        return this.exercises.stream()
                .filter(exercise -> exercise.getType().equals(ExerciseTypes.PROGRAMMING))
                .map(exercise -> (ProgrammingExercise) exercise)
                .collect(Collectors.toSet());
    }

    public Collection<FileUploadExercise> getFileUploadExercises() {
        return this.exercises.stream()
                .filter(exercise -> exercise.getType().equals(ExerciseTypes.FILE_UPLOAD))
                .map(exercise -> (FileUploadExercise) exercise)
                .collect(Collectors.toSet());
    }
}
