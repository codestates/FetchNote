package com.Team4.FetchNoteServer.Entity;

import javax.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Patches.class)
    @JoinColumn(name = "patch_id")
    private Patches patches;

    @Column(nullable = false)
    private String imagename;

    @Column(nullable = false)
    private String imagetype;

    @Column(nullable = false, length = 2048)
    private byte[] imagebyte;

    public Image() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    public byte[] getImagebyte() {
        return imagebyte;
    }

    public void setImagebyte(byte[] imagebyte) {
        this.imagebyte = imagebyte;
    }

    public Patches getPatches() {
        return patches;
    }

    public void setPatches(Patches patches) {
        this.patches = patches;
    }
}
