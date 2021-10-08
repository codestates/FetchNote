package com.Team4.FetchNoteServer.Service;

import com.Team4.FetchNoteServer.Domain.CommentInput;
import com.Team4.FetchNoteServer.Domain.UserSignUp;
import com.Team4.FetchNoteServer.Entity.Comment;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private static long GET_ID = 0L;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    // controller에서 요청받은 메소드로, repository에 comment 생성 요청을 한다.
    public void CreateCommentData(CommentInput commentInput) {
        GET_ID++;
        commentRepository.CreateComment(commentInput, GET_ID);
    }

    // controller에서 요청받은 메소드로, repository에 comment 삭제 요청을 한다.
    public String DeleteCommentData(Long id) {
        Comment comment = commentRepository.FindById(id);
        if(comment == null) return null;

        commentRepository.RemoveComment(id);
        return "OK";
    }

    // controller에서 요청받은 메소드로, repository에 comment 수정 요청을 한다.
    public String ModifyCommentData(Long id, CommentInput commentInput) {
        String res = commentRepository.ChangeComment(id, commentInput);
        return res;
    }
}
