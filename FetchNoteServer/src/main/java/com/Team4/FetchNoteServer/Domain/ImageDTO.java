package com.Team4.FetchNoteServer.Domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
public class ImageDTO {
    private long imageId;
    private long patchId;
    private String imagename;
    private String imagetype;
    private String address;

    public ImageDTO() {}
}
