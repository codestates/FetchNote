package com.Team4.FetchNoteServer.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(columnDefinition = "integer default 0")
    private int exp;

    @OneToMany(mappedBy = "user")
    private List<PatchComment> comments = new ArrayList<>();

    public User() {}
    
    public List<PatchComment> getComments() {
        return comments;
    }

    public void setComments(List<PatchComment> comments) {
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
