package com.Team4.FetchNoteServer.Repository;

import com.Team4.FetchNoteServer.Domain.CommentInput;
import com.Team4.FetchNoteServer.Domain.UserSignUp;
import com.Team4.FetchNoteServer.Entity.Comment;
import com.Team4.FetchNoteServer.Entity.PatchComment;
import com.Team4.FetchNoteServer.Entity.Patches;
import com.Team4.FetchNoteServer.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CommentRepository {
    private final EntityManager entityManager;

    @Autowired
    public CommentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // DB Comment 테이블에 매개변수 id와 일치하는 유저 정보를 리턴한다.
    public PatchComment FindById(long id) {
        return entityManager.find(PatchComment.class, id);
    }

    // DB Comment 테이블에 매개변수 comment와 id, userId의 데이터를 사용하여 코멘트 정보를 저장한다.
    // 패치에 대한 아이디도 처리해야 한다. ** (패치 엔티티가 생기면!)
    public void CreateComment(CommentInput commentInput) {
        User user = entityManager.find(User.class, commentInput.getUserId());
        Patches patches = entityManager.find(Patches.class, commentInput.getPatchId());
        PatchComment com = new PatchComment();
        com.setUser(user);
        com.setPatch(patches);
        com.setComment(commentInput.getComment());
        com.setCreatedAt(new Date());
        com.setUpdatedAt(new Date());
        entityManager.persist(com);

        entityManager.flush();
        entityManager.close();
    }

    // DB Comment 테이블에 매개변수 id의 데이터를 사용해서 해당 id의 Comment를 삭제한다.
    public void RemoveComment(Long id) {
        PatchComment comment = entityManager.find(PatchComment.class, id);
        entityManager.remove(comment);

        entityManager.flush();
        entityManager.close();
    }

    // DB Comment 테이블에 매개변수 id와 commentInput의 데이터를 사용해서 해당 id의 Comment를 수정한다.
    public String ChangeComment(Long id, CommentInput commentInput) {
        PatchComment comment = entityManager.find(PatchComment.class, id);
        boolean isChange = false;
        if(comment == null) return "not found";
        // 코멘트가 달라야 수정이 가능하다.
        // 나중에 패치 엔티티가 추가되면 패치아이디도 추가하여 수정할 것 **
        // 비교하는 것에 패치아디만 and로 처리하면 된다.
        if(comment.getUser().getId() == commentInput.getUserId() && comment.getPatch().getId() == commentInput.getPatchId() &&!comment.getComment().equals(commentInput.getComment())) {
            isChange = true;
            comment.setComment(commentInput.getComment());
            comment.setUpdatedAt(new Date());
        }

        if(isChange) return "modify";
        else return "same";
    }
}
