package com.shirkanesi.artemistutormapper.logic;

import com.shirkanesi.artemistutormapper.ArtemisTutorMapper;
import com.shirkanesi.artemistutormapper.model.authentication.AuthenticationRequest;
import com.shirkanesi.artemistutormapper.model.authentication.AuthenticationResponse;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import lombok.extern.slf4j.Slf4j;

import javax.security.auth.login.LoginException;
import java.io.IOException;

@Slf4j
public class AuthenticationService {

    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient();

    private String token;

    private final String username;
    private final String password;

    public AuthenticationService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getToken() {
        if (token == null) {
            token = acquireAuthenticationToken();
        }
        return token;
    }

    private String acquireAuthenticationToken() {
        try {
            AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(username).password(password).build();

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), ArtemisTutorMapper.OBJECT_MAPPER.writeValueAsString(authenticationRequest));
            Request build = new Request.Builder().url(ArtemisTutorMapper.ARTEMIS_BASE_URL + "/api/authenticate").post(requestBody).build();

            Call call = HTTP_CLIENT.newCall(build);
            Response execute = call.execute();

            if (execute.code() != 200) {
                throw new RuntimeException("Could not authenticate with given credentials!");
            }

            log.info("Authenticated as {} on {}", username, ArtemisTutorMapper.ARTEMIS_BASE_URL);
            return AuthenticationResponse.fromJson(execute.body().string()).getIdToken();
        } catch (IOException e) {
            log.error("Could not authenticate with given credentials", e);
            throw new RuntimeException(e);
        }
    }

}
