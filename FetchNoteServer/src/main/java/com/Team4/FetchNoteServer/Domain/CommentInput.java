package com.Team4.FetchNoteServer.Domain;

public class CommentInput {
    Long userId;
    Long patchId;
    String comment;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPatchId() {
        return patchId;
    }

    public void setPatchId(Long patchId) {
        this.patchId = patchId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
