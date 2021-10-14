package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.GameDTO;
import com.Team4.FetchNoteServer.Domain.LikeGameDTO;
import com.Team4.FetchNoteServer.Entity.Game;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Service.GameService;
import com.Team4.FetchNoteServer.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final UserService userService;
    private final EntityManager entityManager;

    //        "games": [게임정보1, 게임정보2, ...],
    //        "message": "OK"
    @GetMapping(value = "/game")
    public ResponseEntity<?> GetAllGame(@RequestHeader(required = false) Map<String, String> header,
                                        @RequestParam(required = false) boolean prefer){
        // 선호 체크 O => 선호게임만 표시
        if(prefer) {
            try {
                HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
                long userId = userService.FindUserByEmail((String) userInfo.get("email")).getId();

                List<Game> list = gameService.GetGameByUserId(userId);
                List<GameDTO> result = new ArrayList<>();

                for(Game game : list){
                    GameDTO el = new GameDTO();
                    el.setId(game.getId());
                    el.setName(game.getName());
                    el.setImage(game.getImage().toString());
                    result.add(el);
                }

                return ResponseEntity.ok().body(
                        new HashMap<>(){
                            {
                                put("games", result);
                                put("message", "ok");
                            }
                        });
            } catch (NullPointerException e) {
                return ResponseEntity.badRequest().body(
                        new HashMap<>(){{put("message", "invalid user");}});
            }
        // 선호 체크 X => 모든게임 표시
        } else {
            List<Game> list = gameService.GetAllGame();
            List<GameDTO> result = new ArrayList<>();

            for(Game game : list){
                GameDTO el = new GameDTO();
                el.setId(game.getId());
                el.setName(game.getName());
                el.setImage(game.getImage().toString());
                result.add(el);
            }

            return ResponseEntity.ok().body(
                new HashMap<>(){
                    {
                        put("games", result);
                        put("message", "ok");
                    }
                }
            );
        }
    }

    @PostMapping(value = "/game")
    public ResponseEntity<?> SubscribeGamesList(@RequestHeader Map<String, String> header,
                                                @RequestBody LikeGameDTO gameId){
        try {
            HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
            User user = userService.FindUserByEmail((String) userInfo.get("email"));

            try {
                Game game = entityManager.find(Game.class, gameId.getGameId());
                gameService.SubscribeGame(user, game);

                return ResponseEntity.ok().body(
                        new HashMap<>(){{put("message", "ok");}});

            //    404
            //    "message": "the game not exists"
            } catch (NullPointerException e) {
                return ResponseEntity.badRequest().body(
                        new HashMap<>(){{put("message", "the game not exists");}});
            }

        //    404
        //    "message": "invalid user"
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(
                    new HashMap<>(){{put("message", "invalid user");}});
        }
    }
}
