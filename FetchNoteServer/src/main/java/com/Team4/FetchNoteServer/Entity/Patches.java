package com.Team4.FetchNoteServer.Entity;

import javax.persistence.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Patches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(targetEntity = Game.class)
    @JoinColumn(nullable = false, name = "game_id")
    private Game game;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String body;

    @Column(columnDefinition = "integer default 0")
    private int right;

    @Column(columnDefinition = "integer default 0")
    private int wrong;

    @Column(name = "created_at", columnDefinition = "datetime default now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime default now()")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToMany(mappedBy = "patches")
    private List<PatchComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "patches")
    private List<CheckedPatch> checkedPatches = new ArrayList<>();

    @OneToMany(mappedBy = "patches")
    private List<Image> images = new ArrayList<>();

    public Patches() {}

    public List<PatchComment> getComments() {
        return comments;
    }

    public void setComments(List<PatchComment> comments) {
        this.comments = comments;
    }

    public List<CheckedPatch> getCheckedPatches() {
        return checkedPatches;
    }

    public void setCheckedPatches(List<CheckedPatch> checkedPatches) {
        this.checkedPatches = checkedPatches;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}