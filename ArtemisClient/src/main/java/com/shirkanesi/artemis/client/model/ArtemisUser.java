package com.shirkanesi.artemis.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * This class models an ArtemisUser. This can either be a Student or an Assessor.
 */
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtemisUser {

    @JsonProperty
    private int id;

    @JsonProperty
    private String login;

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String email;

    @JsonProperty
    private boolean activated;

    @JsonProperty
    private String langKey;

    @JsonProperty
    private Date lastNotificationRead;

    @JsonProperty
    private String name;

    @JsonProperty
    private String participantIdentifier;

}
