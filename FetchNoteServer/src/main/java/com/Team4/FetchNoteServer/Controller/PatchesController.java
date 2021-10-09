package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.PatchesDTO;
import com.Team4.FetchNoteServer.Entity.Game;
import com.Team4.FetchNoteServer.Entity.Patches;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Service.PatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PatchesController {

    private final EntityManager entityManager;
    private final PatchesService patchesService;

    @Autowired
    public PatchesController(EntityManager entityManager, PatchesService patchesService) {
        this.entityManager = entityManager;
        this.patchesService = patchesService;
    }

    @GetMapping(value = "/patches")
    public ResponseEntity<?> GetPatches(@RequestParam(required = false, defaultValue = "-404") long patchesId,
                                        @RequestParam(required = false, defaultValue = "-404") long gameId) {

        if(patchesId == -404 && gameId == -404) {
            return ResponseEntity.badRequest().body(
                    new HashMap<>(){{put("message","Parameter not exists");}}
            );

        //    // case gameId
        //    "patches": [패치정보1, 패정보2, ...],
        //    "message": "OK"
        //    // case patchId
        } else if(patchesId == -404) {
            List<Patches> patchesList = patchesService.GetAllPatches(gameId);
            List<PatchesDTO> result = new ArrayList<>();

            for(Patches el : patchesList){
                PatchesDTO dto = new PatchesDTO();
                dto.setUserId(el.getUser().getId());
                dto.setGameId(el.getGame().getId());
                dto.setTitle(el.getTitle());
                dto.setBody(el.getBody());
                dto.setImage(el.getImage().toString());
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

        //    "info":
        //        "userId": "userId",
        //        "gameId": "gameId",
        //        "title": "title",
        //        "body": "body",
        //        "image": "image",
        //        "right": 0,
        //        "wrong": 0.
        //        "createdAt": "createdAt",
        //        "updatedAt": "updatedAt"
        //    "message": "OK"
        } else if(gameId == -404) {
            Patches patches = patchesService.GetPatches(patchesId);

            return ResponseEntity.ok().body(
                new HashMap<>(){
                    {
                        put("info", new HashMap<>(){
                            {
                                put("userId", patches.getUser().getId());
                                put("gameId", patches.getGame().getId());
                                put("title", patches.getTitle());
                                put("body", patches.getBody());
                                put("image", patches.getImage().toString());
                                put("right", patches.getRight());
                                put("wrong", patches.getWrong());
                                put("createdAt", patches.getCreatedAt());
                                put("updatedAt", patches.getCreatedAt());
                            }
                        });
                        put("message", "ok");
                    }
                }
            );

        } else {
            return ResponseEntity.badRequest().body(
                    new HashMap<>(){{put("message", "Needs to be only one parameter");}}
            );
        }
    }

    @PostMapping(value = "/patches")
    public ResponseEntity<?> AddPatches(@RequestHeader Map<String, String> header,
                                        @RequestBody PatchesDTO data) {

        //        헤더에서 userId 가져오기 -> UserController
        long userId = 1L;

        try{
            User user = entityManager.find(User.class, userId);
            Game game = entityManager.find(Game.class, data.getGameId());

            if(data.getTitle() == null || data.getBody() == null){
                return ResponseEntity.badRequest().body(
                        new HashMap<>(){{put("message", "Invalid Data");}}
                );
            }

            patchesService.RegisterPatches(user, game, data);

            return ResponseEntity.ok().body(
                    new HashMap<>(){{put("message", "ok");}}
            );
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping(value = "/patches")
    public ResponseEntity<?> DeletePatches() {

        return null;
    }

    @PatchMapping(value = "/patches")
    public ResponseEntity<?> PatchPatches() {

        return null;
    }

    @PostMapping(value = "/rating")
    public ResponseEntity<?> RatePatches() {

        return null;
    }
}
