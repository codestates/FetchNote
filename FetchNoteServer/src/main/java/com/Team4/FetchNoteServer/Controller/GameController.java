package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.GameDTO;
import com.Team4.FetchNoteServer.Entity.Game;
import com.Team4.FetchNoteServer.Service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import javax.servlet.http.Cookie;

@RestController
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    //    Load all game list - GET
    //    {
    //        "games": [게임정보1, 게임정보2, ...],
    //        "message": "OK"
    //    }
    @GetMapping(value = "/game")
    public ResponseEntity<?> GetAllGame(@RequestParam(required = false) boolean prefer){
        // 선호 체크 O => 선호게임만 표시
        if(prefer) {
            //유저 검증 코드
            //UserService.token

            long userid = 3L;

            //유저 ID 추출 코드
            //UserRepository.findId

            List<Game> list = gameService.GetGameByUserId(userid);
            List<GameDTO> result = new ArrayList<>();

            for(Game game : list){
                GameDTO el = new GameDTO();
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
        // 선호 체크 X => 모든게임 표시
        } else {
            List<Game> list = gameService.GetAllGame();
            List<GameDTO> result = new ArrayList<>();

            for(Game game : list){
                GameDTO el = new GameDTO();
                el.setName(game.getName());
                el.setImage(game.getImage().toString());
                result.add(el);
            }

            try {
                // dummy data : image send test
                BufferedImage originalImage = ImageIO.read(new File("/Users/gimchan-ug/Desktop/codestates/FetchNote/FetchNoteServer/src/main/resources/test_image.png"));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(originalImage, "png", baos);
                String image = Arrays.toString(baos.toByteArray());

                return ResponseEntity.ok().body(
                        new HashMap<>(){
                            {
                                put("games", result);
                                put("test_image", image);
                                put("message", "ok");
                            }
                        }
                );
            } catch (IOException e) {
                return ResponseEntity.badRequest().body(e);
            }
        }
    }

    //    Subscribe games list - GET
    //    404
    //    "message": "invalid user"
    @PostMapping(value = "/game")
    public ResponseEntity<?> SubscribeGamesList(@RequestBody(required = true) long gameId){

        return ResponseEntity.ok().body("ok");
    }
}
