package com.shirkanesi.artemis.client.model.participation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemis.client.model.submission.ModelingSubmission;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelingParticipation extends Participation {

    @JsonProperty
    private List<ModelingSubmission> submissions;

    public List<ModelingSubmission> getSubmissions() {
        return Collections.unmodifiableList(this.submissions);
    }
}
