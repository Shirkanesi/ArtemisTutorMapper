package com.shirkanesi.artemis.client.model.authentication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequest {

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    @JsonProperty
    @Builder.Default
    private boolean rememberMe = false;

}
