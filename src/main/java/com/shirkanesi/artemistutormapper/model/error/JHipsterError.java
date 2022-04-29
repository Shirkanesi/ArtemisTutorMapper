package com.shirkanesi.artemistutormapper.model.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.ToString;

import static com.shirkanesi.artemistutormapper.ArtemisTutorMapper.OBJECT_MAPPER;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class JHipsterError {

    @JsonProperty
    private String type;

    @JsonProperty
    private String title;

    @JsonProperty
    private int status;

    @JsonProperty
    private String detail;

    @JsonProperty
    private String path;

    @JsonProperty
    private String message;

    public String getFormattedMessage() {
        return String.format("%s (%d): %s", this.title, this.status, this.detail);
    }

    public static JHipsterError fromJson(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, JHipsterError.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
