package com.Team4.FetchNoteServer.Service;

import com.Team4.FetchNoteServer.Domain.CommentInputDTO;
import com.Team4.FetchNoteServer.Entity.PatchComment;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    // controller에서 요청받은 메소드로, repository에 comment 생성 요청을 한다.
    public void CreateCommentData(User user, CommentInputDTO commentInputDTO) {
        commentRepository.CreateComment(user, commentInputDTO);
    }

    // controller에서 요청받은 메소드로, repository에 comment 삭제 요청을 한다.
    public String DeleteCommentData(Long id) {
        PatchComment comment = commentRepository.FindById(id);
        if(comment == null) return null;

        commentRepository.RemoveComment(id);
        return "OK";
    }

    // controller에서 요청받은 메소드로, repository에 comment 수정 요청을 한다.
    public String ModifyCommentData(Long id, CommentInputDTO commentInputDTO) {
        String res = commentRepository.ChangeComment(id, commentInputDTO);
        return res;
    }
}
