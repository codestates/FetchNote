package com.Team4.FetchNoteServer.Repository;

import com.Team4.FetchNoteServer.Domain.PatchesDTO;
import com.Team4.FetchNoteServer.Entity.Game;
import com.Team4.FetchNoteServer.Entity.Patches;
import com.Team4.FetchNoteServer.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Blob;
import java.sql.SQLException;
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
        byte[] byteContent = data.getImage().getBytes();
        Blob image = null;
        try {
            image.setBytes(game.getId(),byteContent);
            patches.setImage(image);
            entityManager.persist(patches);
            entityManager.flush();
            entityManager.close();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }
}
