package com.Team4.FetchNoteServer.Repository;

import com.Team4.FetchNoteServer.Domain.ImageDTO;
import com.Team4.FetchNoteServer.Entity.Image;
import com.Team4.FetchNoteServer.Entity.Patches;
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

    public void ImageUpload(ImageDTO dto) {
        Image i = new Image();
        i.setPatches(entityManager.find(Patches.class, dto.getPatchId()));
        i.setImagename(dto.getImagename());
        i.setImagetype(dto.getImagetype());
        i.setAddress(dto.getAddress());
        entityManager.persist(i);
        entityManager.flush();
        entityManager.close();
    }

    public String ImageDownload(long id) {
        return entityManager.find(Image.class, id).getAddress();
    }

    public String ImageDelete(long id) {
        Image target = entityManager.find(Image.class, id);
        String address = target.getAddress();
        entityManager.remove(target);
        entityManager.flush();
        entityManager.close();
        return address;
    }
}
