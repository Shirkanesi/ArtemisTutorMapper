package com.shirkanesi.artemistutormapper.model.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationResponse {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @JsonProperty("id_token")
    private String idToken = "";

    public static AuthenticationResponse fromJson(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, AuthenticationResponse.class);
        } catch (JsonProcessingException e) {
//            return new AuthenticationResponse();
            throw new RuntimeException(e);
        }
    }

}
