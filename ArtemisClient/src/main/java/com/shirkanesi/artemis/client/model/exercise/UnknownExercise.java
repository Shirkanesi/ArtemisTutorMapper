package com.shirkanesi.artemis.client.model.exercise;

import com.shirkanesi.artemis.client.model.submission.Submission;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shirkanesi.artemis.client.logic.ArtemisClient;
import lombok.ToString;

import java.util.List;

@ToString
@JsonDeserialize
public class UnknownExercise extends Exercise {
    @Override
    public List<? extends Submission> getSubmissionsForExercise(ArtemisClient client) {
        return List.of();
    }

    @Override
    public ExerciseTypes getType() {
        return ExerciseTypes.UNKNOWN;
    }
}
