package com.shirkanesi.artemistutormapper.model.exercise;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

@ToString
public enum ExerciseTypes {

    PROGRAMMING("PROGRAMMING"),

    @JsonEnumDefaultValue
    UNKNOWN("unknown");

    private final String internalName;

    ExerciseTypes(String internalName) {
        this.internalName = internalName;
    }

    @JsonValue
    public String getInternalName() {
        return internalName;
    }
}
