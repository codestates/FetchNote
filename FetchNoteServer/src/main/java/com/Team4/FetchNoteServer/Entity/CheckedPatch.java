package com.Team4.FetchNoteServer.Entity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@DynamicInsert
@Table(name = "checked_patch")
public class CheckedPatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Patches.class)
    @JoinColumn(name = "patch_id")
    private Patches patches;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "boolean default false")
    private boolean isFirst;

    @Column(columnDefinition = "boolean default false")
    private boolean right;

    @Column(columnDefinition = "boolean default false")
    private boolean wrong;

    public CheckedPatch() {}

    public Patches getPatch() {
        return patches;
    }

    public void setPatch(Patches patches) {
        this.patches = patches;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isWrong() {
        return wrong;
    }

    public void setWrong(boolean wrong) {
        this.wrong = wrong;
    }
}
