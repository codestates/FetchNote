package com.Team4.FetchNoteServer.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeGameDTO {
    private long userId;
    private long gameId;

    public LikeGameDTO() {}
}
