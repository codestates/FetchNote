package com.Team4.FetchNoteServer.Domain.Data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {
    private String grant_type = "authorization_code";
    private String client_id;
    private String redirect_uri;
    private String code;

    public UserData() { }

    public UserData(String client_id, String redirect_uri, String code) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
    }
}