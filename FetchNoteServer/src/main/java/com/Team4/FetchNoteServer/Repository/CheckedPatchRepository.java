package com.Team4.FetchNoteServer.Repository;

import com.Team4.FetchNoteServer.Entity.CheckedPatch;
import com.Team4.FetchNoteServer.Entity.Patches;
import com.Team4.FetchNoteServer.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class CheckedPatchRepository {

    private final EntityManager entityManager;

    @Autowired
    public CheckedPatchRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void AddCheckedPatch (Patches patches, long userId){
        List<CheckedPatch> checkedPatches = entityManager
                .createQuery("SELECT el FROM CheckedPatch el " +
                        "WHERE user_id =" + userId + " AND patch_id =" + patches.getId() + "", CheckedPatch.class)
                .getResultList();
        if(checkedPatches.size() == 0) {
            CheckedPatch checkedPatch = new CheckedPatch();
            checkedPatch.setPatch(patches);
            checkedPatch.setUser(entityManager.find(User.class, userId));
            entityManager.persist(checkedPatch);
            entityManager.flush();
            entityManager.close();
        }
    }

    public void RatingPatches(Patches patches, User user, String str){
        List<CheckedPatch> checkedPatches = entityManager
                .createQuery("SELECT el FROM CheckedPatch el " +
                        "WHERE user_id =" + user.getId() + " AND patch_id =" + patches.getId() + "", CheckedPatch.class)
                .getResultList();

        if(checkedPatches.size() != 0) {
            CheckedPatch checkedPatch = checkedPatches.get(0);
            User anotherUser = entityManager.find(User.class, patches.getUser().getId());
            boolean expChange = false;

            if(str.contains("right")) {
                if(!checkedPatch.isRight() && !checkedPatch.isWrong()){
                    patches.setRight(patches.getRight() + 1);
                } else if(checkedPatch.isWrong()){
                    patches.setRight(patches.getRight() + 1);
                    patches.setWrong(patches.getWrong() - 1);
                }
                checkedPatch.setRight(true);
                checkedPatch.setWrong(false);
                if(!checkedPatch.isFirst()) {
                    anotherUser.setExp(anotherUser.getExp() + 5);
                    expChange = true;
                }
            } else if(str.contains("wrong")) {
                if(!checkedPatch.isRight() && !checkedPatch.isWrong()){
                    patches.setWrong(patches.getWrong() + 1);
                } else if(checkedPatch.isRight()){
                    patches.setRight(patches.getRight() - 1);
                    patches.setWrong(patches.getWrong() + 1);
                }
                checkedPatch.setRight(false);
                checkedPatch.setWrong(true);
                if(!checkedPatch.isFirst()) {
                    anotherUser.setExp(anotherUser.getExp() - 3);
                    expChange = true;
                }
            } else {
                if(checkedPatch.isRight()) patches.setRight(patches.getRight() - 1);
                else if(checkedPatch.isWrong()) patches.setWrong(patches.getWrong() - 1);
                checkedPatch.setRight(false);
                checkedPatch.setWrong(false);
            }
            checkedPatch.setFirst(true);

            if(expChange) entityManager.persist(anotherUser);

            entityManager.persist(patches);
            entityManager.persist(checkedPatch);
            entityManager.flush();
            entityManager.close();
        }
    }
}
