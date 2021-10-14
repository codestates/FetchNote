package com.Team4.FetchNoteServer.Domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentOutputDTO {
    private String nickname;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
}
