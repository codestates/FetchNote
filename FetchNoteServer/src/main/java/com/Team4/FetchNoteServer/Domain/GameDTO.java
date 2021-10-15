package com.Team4.FetchNoteServer.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDTO {
    private long id;
    private String name;
    private String image;

    public GameDTO() {}
}
