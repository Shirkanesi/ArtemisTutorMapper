package com.shirkanesi.artemis.client.model.submission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemis.client.logic.ArtemisClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextSubmission extends Submission {

    @JsonProperty
    private String text;

    @Override
    protected Response doLockSubmission(ArtemisClient client) throws IOException {
        String url = String.format(client.getArtemisBaseUrl() + "/api/participations/%d/submissions/%d/for-text-assessment", this.getParticipation().getId(), this.getId());
        Request.Builder request = new Request.Builder().get().url(url);
        return client.makeRequest(request);
    }

    @Override
    protected Response doCancelAssessmentOfSubmission(ArtemisClient client) throws IOException {
        String url = String.format(client.getArtemisBaseUrl() + "/api/participations/%d/submissions/%d/cancel-assessment", this.getParticipation().getId(), this.getId());
        Request.Builder request = new Request.Builder().post(RequestBody.create(null, new byte[0])).url(url);
        return client.makeRequest(request);
    }

}
