package com.Team4.FetchNoteServer.Repository;

import com.Team4.FetchNoteServer.Domain.PatchesDTO;
import com.Team4.FetchNoteServer.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class PatchesRepository {

    private final EntityManager entityManager;

    @Autowired
    public PatchesRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Patches> FindByGameId (long gameId) {
        return entityManager
                .createQuery("SELECT a FROM Patches a WHERE game_id =" + gameId + "", Patches.class)
                .getResultList();
    }

    public Patches FindByPatchesId (long patchesId) {
        return entityManager
                .createQuery("SELECT el FROM Patches el WHERE id =" + patchesId + "", Patches.class)
                .getSingleResult();
    }

    public void RegistPatches (User user, Game game, PatchesDTO data){
        Patches patches = new Patches();

        patches.setUser(user);
        patches.setGame(game);
        patches.setTitle(data.getTitle());
        patches.setBody(data.getBody());

        Date date = new Date();

        patches.setCreatedAt(date);
        patches.setUpdatedAt(date);

        entityManager.persist(patches);
        entityManager.flush();
        entityManager.close();
    }

    public void RemovePatches (Patches patches) {
        long patchesId = patches.getId();
        List<PatchComment> comments = entityManager.createQuery("SELECT el FROM PatchComment el WHERE patch_id =" + patchesId + "", PatchComment.class).getResultList();
        List<CheckedPatch> checkedPatches = entityManager.createQuery("SELECT el FROM CheckedPatch el WHERE patch_id =" + patchesId + "", CheckedPatch.class).getResultList();
        List<Image> images = entityManager.createQuery("SELECT el FROM Image el WHERE patch_id =" + patchesId + "", Image.class).getResultList();

        for(PatchComment el : comments) entityManager.remove(el);
        for(CheckedPatch el : checkedPatches) entityManager.remove(el);
        for(Image el : images) entityManager.remove(el);

        entityManager.remove(patches);
        entityManager.flush();
        entityManager.close();
    }

    public void UpdatePatches(Patches patches, PatchesDTO data) {
        if(data.getTitle() != null) patches.setTitle(data.getTitle());
        if(data.getBody() != null) patches.setBody(data.getBody());
        patches.setUpdatedAt(new Date());

        entityManager.persist(patches);
        entityManager.flush();
        entityManager.close();
    }
}
