package com.shirkanesi.artemistutormapper.model.exercise;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shirkanesi.artemistutormapper.logic.ArtemisClient;
import com.shirkanesi.artemistutormapper.model.submission.Submission;
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
