package com.shirkanesi.artemistutormapper.model.participation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shirkanesi.artemistutormapper.model.submission.FileUploadSubmission;
import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileUploadParticipation extends Participation {

    @JsonProperty
    private List<FileUploadSubmission> submissions;

    public List<FileUploadSubmission> getSubmissions() {
        return Collections.unmodifiableList(submissions);
    }
}
