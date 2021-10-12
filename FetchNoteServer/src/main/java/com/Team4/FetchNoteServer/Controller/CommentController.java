package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.CommentInput;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Repository.CommentRepository;
import com.Team4.FetchNoteServer.Service.CommentService;
import com.Team4.FetchNoteServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin("https://localhost:3000")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    // 새로운 코멘트를 작성하는 http메소드 매핑
    @PostMapping(value = "/comment")
    public ResponseEntity<?> AddComment(@RequestBody CommentInput commentInput) {
        boolean flag = true;
        if(commentInput.getPatchId() == null || commentInput.getUserId() == null
        || commentInput.getComment() == null) flag = false;
        // commentInput 객체 내에 기입하지 않은 경우
        if(!flag) {
            return ResponseEntity.badRequest().body(new HashMap<>(){{
                put("message", "insufficient parameters supplied");
            }});
        }

        commentService.CreateCommentData(commentInput);
        return ResponseEntity.ok().body(new HashMap<>() {{
            put("message", "OK");
        }});
    }
    // 해당 코멘트에 대한 삭제를 진행하는 http메소드 매핑
    @DeleteMapping(value = "/comment")
    public ResponseEntity<?> DeleteComment(@RequestParam(value = "commentId") Long id) {
        String msg = commentService.DeleteCommentData(id);
        if(msg == null) return ResponseEntity.status(404).body(new HashMap<>() {{
            put("message", "Not found comment");
        }});
        else return ResponseEntity.ok().body(new HashMap<>() {{
            put("message", "OK");
        }});
    }

    // 해당 코멘트에 대한 수정은 진행하는 http메소드 매핑
    @PatchMapping(value = "/comment")
    public ResponseEntity<?> ModifyComment(@RequestParam(value = "commentId") Long id, @RequestBody CommentInput commentInput) {
        String res = commentService.ModifyCommentData(id, commentInput);
        if(res.equals("not found")) return ResponseEntity.status(404).body(new HashMap<>() {{
            put("message", "Not found comment");
        }});
        else if(res.equals("same")) return ResponseEntity.badRequest().body(new HashMap<>() {{
            put("message", "invalid userId or patchId");
        }});
        else return ResponseEntity.status(404).body(new HashMap<>() {{
            put("message", "OK");
        }});
    }
}
