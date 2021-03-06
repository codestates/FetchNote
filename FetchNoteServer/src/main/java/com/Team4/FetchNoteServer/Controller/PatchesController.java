package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.CommentOutputDTO;
import com.Team4.FetchNoteServer.Domain.PatchesDTO;
import com.Team4.FetchNoteServer.Entity.Game;
import com.Team4.FetchNoteServer.Entity.PatchComment;
import com.Team4.FetchNoteServer.Entity.Patches;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Repository.CheckedPatchRepository;
import com.Team4.FetchNoteServer.Repository.PatchesRepository;
import com.Team4.FetchNoteServer.Service.PatchesService;
import com.Team4.FetchNoteServer.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PatchesController {

    private final EntityManager entityManager;
    private final PatchesService patchesService;
    private final PatchesRepository patchesRepository;
    private final CheckedPatchRepository checkedPatchRepository;
    private final UserService userService;

    private final HashMap<String,String> messageOk = new HashMap<>(){{put("message", "ok");}};

    @GetMapping(value = "/patches")
    public ResponseEntity<?> GetPatches(@RequestHeader Map<String, String> header,
                                        @RequestParam(required = false, defaultValue = "-404") long patchesId,
                                        @RequestParam(required = false, defaultValue = "-404") long gameId) {

        if(patchesId == -404 && gameId == -404) {
            return ResponseEntity.badRequest().body(
                    new HashMap<>(){{put("message","Parameter not exists");}}
            );

        //    // case gameId
        } else if(patchesId == -404) {
            List<Patches> patchesList = patchesService.GetAllPatches(gameId);
            List<PatchesDTO> result = new ArrayList<>();

            for(Patches el : patchesList){
                PatchesDTO dto = new PatchesDTO();
                dto.setPatchesId(el.getId());
                dto.setUserId(el.getUser().getId());
                dto.setGameId(el.getGame().getId());
                dto.setTitle(el.getTitle());
                dto.setBody(el.getBody());
                dto.setRight(el.getRight());
                dto.setWrong(el.getWrong());
                dto.setCreatedAt(el.getCreatedAt());
                dto.setUpdatedAt(el.getUpdatedAt());
                result.add(dto);
            }

            return ResponseEntity.ok().body(
                new HashMap<>(){
                    {
                        put("patches", result);
                        put("message", "ok");
                    }
                }
            );

        //    // case patchId
        } else if(gameId == -404) {
            try {
                Patches patches = patchesService.GetPatches(patchesId);

                HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
                User user = userService.FindUserByEmail((String) userInfo.get("email"));

                // ????????? ????????? CheckedPatch ??????
                checkedPatchRepository.AddCheckedPatch(patches, user.getId());

                List<PatchComment> commentList = patches.getComments();
                List<CommentOutputDTO> comments = new ArrayList<>();

                for(PatchComment el : commentList){
                    CommentOutputDTO dto = new CommentOutputDTO();
                    dto.setComment(el.getComment());
                    dto.setNickname(el.getUser().getNickname());
                    dto.setCreatedAt(el.getCreatedAt());
                    dto.setUpdatedAt(el.getUpdatedAt());
                    comments.add(dto);
                }

                return ResponseEntity.ok().body(
                        new HashMap<>(){
                            {
                                put("info", new HashMap<>(){
                                    {
                                        put("userId", patches.getUser().getId());
                                        put("gameId", patches.getGame().getId());
                                        put("title", patches.getTitle());
                                        put("body", patches.getBody());
                                        put("right", patches.getRight());
                                        put("wrong", patches.getWrong());
                                        put("createdAt", patches.getCreatedAt());
                                        put("updatedAt", patches.getCreatedAt());
                                    }
                                });
                                put("comments", comments);
                                put("message", "ok");
                            }
                        }
                );
            } catch (NullPointerException e) {
                return ResponseEntity.badRequest().body(e);
            }
        } else {
            return ResponseEntity.badRequest().body(
                    new HashMap<>(){{put("message", "Needs to be only one parameter");}}
            );
        }
    }

    @PostMapping(value = "/patches")
    public ResponseEntity<?> AddPatches(@RequestHeader Map<String, String> header,
                                        @RequestBody PatchesDTO data) {

        //        ???????????? userId ???????????? -> UserController
        try{
            HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
            User user = userService.FindUserByEmail((String) userInfo.get("email"));
            Game game = entityManager.find(Game.class, data.getGameId());

            if(data.getTitle() == null || data.getBody() == null){
                return ResponseEntity.badRequest().body(
                        new HashMap<>(){{put("message", "Invalid Data");}}
                );
            }

            Patches patches = patchesService.RegisterPatches(user, game, data);
            checkedPatchRepository.AddCheckedPatch(patches, user.getId());

            return ResponseEntity.ok().body(new HashMap<>(){
                {
                    put("id", patches.getId());
                    put("message", "ok");
                }
            });
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping(value = "/patches")
    public ResponseEntity<?> DeletePatches(@RequestHeader Map<String, String> header,
                                           @RequestBody PatchesDTO data) {
        try {
            HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
            User user = userService.FindUserByEmail((String) userInfo.get("email"));
            long userId = user.getId();

            Patches patches = entityManager.find(Patches.class, data.getPatchesId());
            if(patches.getUser().getId() == userId){
                patchesRepository.RemovePatches(patches);
                return ResponseEntity.ok().body(messageOk);
            } else {
                return ResponseEntity.badRequest().body(
                        new HashMap<>(){{put("message", "Invalid User");}}
                );
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    //    title
    //    body
    @PatchMapping(value = "/patches/{patchesId}")
    public ResponseEntity<?> PatchPatches(@PathVariable(value = "patchesId") Long patchesId,
                                          @RequestHeader Map<String, String> header,
                                          @RequestBody PatchesDTO data) {
        try {
            HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
            User user = userService.FindUserByEmail((String) userInfo.get("email"));
            long userId = user.getId();

            Patches patches = entityManager.find(Patches.class, patchesId);
            if(patches.getUser().getId() == userId){
                patchesRepository.UpdatePatches(patches, data);
                return ResponseEntity.ok().body(messageOk);
            } else {
                return ResponseEntity.badRequest().body(
                        new HashMap<>(){{put("message", "Invalid User");}}
                );
            }
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping(value = "/rating/{patchesId}")
    public ResponseEntity<?> RatePatches(@PathVariable(value = "patchesId") Long patchesId,
                                         @RequestHeader Map<String, String> header,
                                         @RequestBody String rating) {
        try {
            HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
            User user = userService.FindUserByEmail((String) userInfo.get("email"));
            long userId = user.getId();

            Patches patches = entityManager.find(Patches.class, patchesId);
            if(userId == patches.getUser().getId()){
                return ResponseEntity.badRequest().body(
                        new HashMap<>(){{put("message", "Can not recommend your own post");}}
                );
            }
            checkedPatchRepository.RatingPatches(patches, user, rating);
            return ResponseEntity.ok().body(messageOk);
        } catch (NullPointerException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
}
