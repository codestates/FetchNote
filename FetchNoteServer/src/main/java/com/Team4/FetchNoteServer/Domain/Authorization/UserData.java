package com.Team4.FetchNoteServer.Domain.Authorization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {
    private String grant_type = "authorization_code";
    private String clientId;
    private String redirectUri = "https://localhost:8080/oauth";
    private String code;

    public UserData() { }

    public UserData(String clientId, String code) {
        this.clientId = clientId;
        this.code = code;
    }
}