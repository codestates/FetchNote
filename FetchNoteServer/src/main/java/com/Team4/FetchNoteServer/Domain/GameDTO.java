package com.Team4.FetchNoteServer.Domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
public class GameDTO {
    private String name;
    private String image;

    public GameDTO() {}
}
