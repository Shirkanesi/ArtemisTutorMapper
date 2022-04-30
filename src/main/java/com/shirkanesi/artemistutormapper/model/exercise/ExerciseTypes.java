package com.shirkanesi.artemistutormapper.model.exercise;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

import java.util.Arrays;

@ToString
public enum ExerciseTypes {

    PROGRAMMING("programming", ProgrammingExercise.class),
    FILE_UPLOAD("file-upload", FileUploadExercise.class),

    TEXT("text", TextExercise.class),

    MODELING("modeling", ModelingExercise.class),

    @JsonEnumDefaultValue
    UNKNOWN("unknown", UnknownExercise.class);

    private final String internalName;
    private final Class<? extends Exercise> objectClass;

    ExerciseTypes(String internalName, Class<? extends Exercise> objectClass) {
        this.internalName = internalName;
        this.objectClass = objectClass;
    }

    public static ExerciseTypes getByInternalName(String name) {
        return Arrays.stream(ExerciseTypes.values())
                .filter(exerciseTypes -> exerciseTypes.getInternalName().equalsIgnoreCase(name))
//                .filter(exerciseTypes -> !exerciseTypes.equals(UNKNOWN))
                .findFirst().orElse(ExerciseTypes.UNKNOWN);
    }

    @JsonValue
    public String getInternalName() {
        return internalName;
    }

    public Class<? extends Exercise> getObjectClass() {
        return objectClass;
    }
}
