package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.CommentInputDTO;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Service.CommentService;
import com.Team4.FetchNoteServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    // 새로운 코멘트를 작성하는 http메소드 매핑
    @PostMapping(value = "/comment")
    public ResponseEntity<?> AddComment(@RequestBody CommentInputDTO commentInputDTO,
                                        @RequestHeader Map<String, String> header) {
        HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
        User user = userService.FindUserByEmail((String) userInfo.get("email"));

        boolean flag = true;
        if(commentInputDTO.getPatchId() == null || commentInputDTO.getComment() == null) flag = false;
        // commentInput 객체 내에 기입하지 않은 경우
        if(!flag) {
            return ResponseEntity.badRequest().body(new HashMap<>(){{
                put("message", "insufficient parameters supplied");
            }});
        }

        commentService.CreateCommentData(user,commentInputDTO);
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
    public ResponseEntity<?> ModifyComment(@RequestParam(value = "commentId") Long id, @RequestBody CommentInputDTO commentInputDTO) {
        String res = commentService.ModifyCommentData(id, commentInputDTO);
        if(res.equals("not found")) return ResponseEntity.status(404).body(new HashMap<>() {{
            put("message", "Not found comment");
        }});
        else if(res.equals("same")) return ResponseEntity.badRequest().body(new HashMap<>() {{
            put("message", "invalid userId or patchId");
        }});
        else return ResponseEntity.ok().body(new HashMap<>() {{
            put("message", "OK");
        }});
    }
}
