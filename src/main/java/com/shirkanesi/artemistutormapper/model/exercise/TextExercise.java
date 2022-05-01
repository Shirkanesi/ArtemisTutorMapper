package com.shirkanesi.artemistutormapper.model.exercise;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shirkanesi.artemistutormapper.logic.ArtemisClient;
import com.shirkanesi.artemistutormapper.model.submission.TextSubmission;
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
public class TextExercise extends Exercise {


    @Override
    public List<TextSubmission> getSubmissionsForExercise(ArtemisClient client) {
        String url = String.format(client.getArtemisBaseUrl() + "/api/exercises/%d/text-submissions?submittedOnly=true", this.getId());
        Request.Builder request = new Request.Builder().get().url(url);
        try {
            Response response = client.makeRequest(request);
            TextSubmission[] submissions = OBJECT_MAPPER.readValue(response.body().string(), TextSubmission[].class);
            return Arrays.asList(submissions);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not load submissions for exercise %d", this.getId()), e);
        }
    }
}
