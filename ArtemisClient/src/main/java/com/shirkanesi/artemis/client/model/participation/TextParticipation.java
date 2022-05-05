package com.shirkanesi.artemis.client.model.participation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemis.client.model.submission.TextSubmission;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextParticipation extends Participation {

    @JsonProperty
    private List<TextSubmission> submissions;

    public List<TextSubmission> getSubmissions() {
        return Collections.unmodifiableList(this.submissions);
    }
}
