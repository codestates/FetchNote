package com.Team4.FetchNoteServer.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
public class ImageRepository {

    private final EntityManager entityManager;

    @Autowired
    public ImageRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void ImageUpload() {

    }

    public void ImageDownload() {

    }

    public void ImageDelete() {

    }
}
