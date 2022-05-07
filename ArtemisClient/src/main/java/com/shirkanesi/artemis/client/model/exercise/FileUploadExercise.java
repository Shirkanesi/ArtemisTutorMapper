package com.shirkanesi.artemis.client.model.exercise;

import com.shirkanesi.artemis.client.model.submission.FileUploadSubmission;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shirkanesi.artemis.client.logic.ArtemisClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.ToString;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.shirkanesi.artemis.client.logic.ArtemisClient.OBJECT_MAPPER;

@ToString
@JsonDeserialize
public class FileUploadExercise extends Exercise {

    @Override
    public List<FileUploadSubmission> getSubmissionsForExercise(ArtemisClient client) {
        String url = String.format(client.getArtemisBaseUrl() + "/api/exercises/%d/file-upload-submissions?submittedOnly=true", this.getId());
        Request.Builder request = new Request.Builder().get().url(url);
        try {
            Response response = client.makeRequest(request);
            FileUploadSubmission[] submissions = OBJECT_MAPPER.readValue(response.body().string(), FileUploadSubmission[].class);
            return Arrays.asList(submissions);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not load submissions for exercise %d", this.getId()), e);
        }
    }

}
