package com.Team4.FetchNoteServer.Domain.Authorization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {

    private String token_type = "bearer";
    private String access_token;
    private  int expires_in;
    private String refresh_token;
    private int refresh_token_expires_in;

}