package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Service.PatchesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.HashMap;

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

            return null;

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

            return null;

        } else {
            return ResponseEntity.badRequest().body(
                    new HashMap<>(){{put("message", "Needs to be only one parameter");}}
            );
        }
    }

    @PostMapping(value = "/patches")
    public ResponseEntity<?> AddPatches() {

        return null;
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
