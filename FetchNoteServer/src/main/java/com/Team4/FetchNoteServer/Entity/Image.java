package com.Team4.FetchNoteServer.Entity;

import javax.persistence.*;
import java.sql.Blob;

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
    private String address;

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

    public Patches getPatches() {
        return patches;
    }

    public void setPatches(Patches patches) {
        this.patches = patches;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
