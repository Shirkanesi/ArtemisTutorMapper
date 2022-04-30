package com.shirkanesi.artemistutormapper.model.submission;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemistutormapper.logic.ArtemisClient;
import com.shirkanesi.artemistutormapper.model.participation.ProgrammingParticipation;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.shirkanesi.artemistutormapper.ArtemisTutorMapper.ARTEMIS_BASE_URL;

@Slf4j
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgrammingSubmission extends Submission {

    @JsonProperty
    private String commitHash;

    @JsonProperty
    private boolean buildFailed = false;

    @JsonProperty
    private boolean buildArtifact = false;

    @JsonProperty
    private ProgrammingParticipation participation;

    @Override
    protected Response doLockSubmission(ArtemisClient client) throws IOException {
        String url = String.format(ARTEMIS_BASE_URL + "/api/programming-submissions/%d/lock", this.getId());
        Request.Builder request = new Request.Builder().get().url(url);
        return client.makeRequest(request);
    }

    @Override
    protected Response doCancelAssessmentOfSubmission(ArtemisClient client) throws IOException {
        String url = String.format(ARTEMIS_BASE_URL + "/api/programming-submissions/%d/cancel-assessment", this.getId());
        Request.Builder request = new Request.Builder().put(RequestBody.create(null, new byte[0])).url(url);
        return client.makeRequest(request);
    }
}
