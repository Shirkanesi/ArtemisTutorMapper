package com.shirkanesi.artemistutormapper.model.submission;

import com.shirkanesi.artemistutormapper.logic.ArtemisClient;
import com.shirkanesi.artemistutormapper.model.error.JHipsterError;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;

import javax.print.attribute.DocAttribute;
import java.io.IOException;

import static com.shirkanesi.artemistutormapper.ArtemisTutorMapper.ARTEMIS_BASE_URL;

@Slf4j
public class ProgrammingSubmission extends Submission {

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
