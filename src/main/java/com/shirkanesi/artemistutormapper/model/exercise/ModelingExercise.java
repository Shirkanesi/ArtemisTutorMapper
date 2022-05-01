package com.shirkanesi.artemistutormapper.model.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shirkanesi.artemistutormapper.logic.ArtemisClient;
import com.shirkanesi.artemistutormapper.model.submission.ModelingSubmission;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.shirkanesi.artemistutormapper.logic.ArtemisClient.OBJECT_MAPPER;

@Getter
@ToString
@JsonDeserialize
public class ModelingExercise extends Exercise {

    @JsonProperty
    private String diagramType;

    @JsonProperty
    private String exampleSolutionModel;

    @Override
    public List<ModelingSubmission> getSubmissionsForExercise(ArtemisClient client) {
        String url = String.format(client.getArtemisBaseUrl() + "/api/exercises/%d/modeling-submissions?submittedOnly=true", this.getId());
        Request.Builder request = new Request.Builder().get().url(url);
        try {
            Response response = client.makeRequest(request);
            ModelingSubmission[] submissions = OBJECT_MAPPER.readValue(response.body().string(), ModelingSubmission[].class);
            return Arrays.asList(submissions);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not load submissions for exercise %d", this.getId()), e);
        }
    }

}
