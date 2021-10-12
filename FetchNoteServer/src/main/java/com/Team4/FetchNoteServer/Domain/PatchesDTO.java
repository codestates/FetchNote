package com.Team4.FetchNoteServer.Domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
public class PatchesDTO {
    private long patchesId;
    private long userId;
    private long gameId;
    private String title;
    private String body;
    private int right;
    private int wrong;
    private Date createdAt;
    private Date updatedAt;

    public PatchesDTO() {}
}
