package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.UserChangeInfoDTO;
import com.Team4.FetchNoteServer.Domain.UserSignUpDTO;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Repository.UserRepository;
import com.Team4.FetchNoteServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class OAuthController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public OAuthController(UserService userService, UserRepository userRepository){
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/oauth")
    public ResponseEntity<?> OAuthLogin(@RequestParam("code") String code, HttpServletRequest request){
        // OAuth를 통한 로그인 구현, 전해받은 인가 코드로 액세스 토큰을 받은 후 유저 정보를 가져오고 서버 데이터베이스에 유저 정보가 있는지 확인하고, 없다면 이메일과 닉네임을 데이터베이스에 저장한다.
        try{
            System.out.println(code);
            String access_Token = userService.getAccessToken(code);
            HashMap<String, Object> userInfo = userService.getUserInfo(access_Token);
            System.out.println("login Controller : " + userInfo);

            // DB에 해당 유저정보가 있는지 확인한다.
            User user = userService.FindUserByEmail((String)userInfo.get("email"));

            // 유저정보가 없다면, DB > User table에 저장을 한다.
            if(user == null) {
                UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
                userSignUpDTO.setEmail((String)userInfo.get("email"));
                userSignUpDTO.setNickname((String)userInfo.get("nickname"));
                userService.CreateUserData(userSignUpDTO);
            }

            System.out.println("at : "+ access_Token);
            return ResponseEntity.ok().body(access_Token);
        }catch (Exception error){
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> OAuthLogOut(@RequestHeader Map<String, String> header) {
        try {
            // 헤더로 받아  액세스 토큰을 매개로 카카오 로그아웃을 진행한다.
            userService.userLogout(header.get("authorization"));

            return ResponseEntity.ok().body(new HashMap<>() {{
                put("userinfo", null);
                put("message", "OK");
            }});
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getUserInfo(@RequestHeader Map<String, String> header,
                                         @RequestParam(required = false, defaultValue = "-404") long userId) {
        if(userId == -404) {
            try {
                System.out.println("hat : " + header.get("authorization"));
                // 헤더로 받아 온 액세스 토큰을 매개로 유저 정보를 가져온다.
                HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
                // 이메일을 통해 DB를 탐색하고 탐색한 유저의 정보를 전해준다.
                User user = userService.FindUserByEmail((String) userInfo.get("email"));
                // DB에 유저정보가 없다면, 아래와 같은 응답을 보내준다.
                if (user == null) {
                    return ResponseEntity.badRequest().body(new HashMap<>() {{
                        put("userinfo", null);
                        put("message", "The user does not exist.");
                    }});
                }
                // DB에 유저정보가 있을 때, 유저의 정보를 아래와 같이 보내준다.
                return ResponseEntity.ok().body(new HashMap<>() {{
                    put("userinfo", new HashMap<>() {{
                        put("id", user.getId());
                        put("email", user.getEmail());
                        put("nickname", user.getNickname());
                        put("exp", user.getExp());
                        // 세션확인 용도 나중에는 지워야한다. **
                        // put("session", session.getId());
                    }});
                    put("message", "OK");
                }});
            } catch (Exception e) {
                return ResponseEntity.status(500).body("err");
            }
        } else {
            try {
                User user = userRepository.FindById(userId);
                return ResponseEntity.ok().body(new HashMap<>(){
                    {
                        put("info", new HashMap<>(){
                            {
                                put("nickname",user.getNickname());
                                put("exp",user.getExp());
                            }
                        });
                        put("message","ok");
                    }
                });
            } catch (NullPointerException e) {
                return ResponseEntity.badRequest().body(new HashMap<>(){
                    {
                        put("message", "can not find user");
                    }
                });
            }
        }
    }

    @PatchMapping(value = "/user")
    public ResponseEntity<?> ChangeUserInfo(@RequestHeader Map<String, String> header, @RequestBody(required = true) UserChangeInfoDTO userChangeInfoDTO) {
        try {
            // 헤더로 받아 온 액세스 토큰을 매개로 유저 정보를 가져온다.
            HashMap<String, Object> userInfo = userService.getUserInfo(header.get("authorization"));
            // 이메일을 통해 DB를 탐색하고 탐색한 유저의 정보를 전해준다.
            User user = userService.FindUserByEmail((String)userInfo.get("email"));

            // DB에 유저정보가 없다면, 아래와 같은 응답을 보내준다.
            if(user == null) {
                return ResponseEntity.badRequest().body(new HashMap<>() {{
                    put("userinfo", null);
                    put("message", "The user does not exist.");
                }});
            }

            // 유저 정보를 수정한다. res는 정보가 바뀌었는지 알 수 있는 결과이다.
            String res = userService.changeUserData(userChangeInfoDTO, user);
            if(res.equals("not found")) return ResponseEntity.status(404).body(new HashMap<>() {{
                put("message", "Not found nickname.");
            }});
            else if(res.equals("same")) return ResponseEntity.badRequest().body(new HashMap<>() {{
                put("message", "invalid nickname.");
            }});
            else return ResponseEntity.ok().body(new HashMap<>() {{
                    put("message", "OK");
                }});
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}
