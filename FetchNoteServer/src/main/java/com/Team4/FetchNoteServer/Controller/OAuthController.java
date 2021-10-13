package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.UserChangeInfoDTO;
import com.Team4.FetchNoteServer.Domain.UserSignUpDTO;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class OAuthController {

    private final UserService userService;

    @Autowired
    public OAuthController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/oauth")
    public ResponseEntity<?> OAuthLogin(@RequestParam("code") String code, HttpSession session){
        // OAuth를 통한 로그인 구현, 전해받은 인가 코드로 액세스 토큰을 받은 후 유저 정보를 가져오고 서버 데이터베이스에 유저 정보가 있는지 확인하고, 없다면 이메일과 닉네임을 데이터베이스에 저장한다.
        try{
            System.out.println(code);
            String access_Token = userService.getAccessToken(code);
            HashMap<String, Object> userInfo = userService.getUserInfo(access_Token);
            System.out.println("login Controller : " + userInfo);

            // 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
            if (userInfo.get("email") != null) {
                session.setAttribute("email", userInfo.get("email"));
                session.setAttribute("access_Token", access_Token);
            }

            // DB에 해당 유저정보가 있는지 확인한다.
            User user = userService.FindUserByEmail((String)userInfo.get("email"));

            // 유저정보가 없다면, DB > User table에 저장을 한다.
            if(user == null) {
                UserSignUpDTO userSignUpDTO = new UserSignUpDTO();
                userSignUpDTO.setEmail((String)userInfo.get("email"));
                userSignUpDTO.setNickname((String)userInfo.get("nickname"));
                userService.CreateUserData(userSignUpDTO);
            }

            return ResponseEntity.ok().body(new HashMap<>() {{
                put("message", "OK");
            }});
        }catch (Exception error){
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<?> OAuthLogOut(HttpSession session) {
        try {
            // 세션에 저장한 액세스 토큰을 매개로 카카오 로그아웃을 진행하고, 세션의 키들을 지운다
            userService.userLogout((String)session.getAttribute("access_Token"));
            session.removeAttribute("access_Token");
            session.removeAttribute("email");

            return ResponseEntity.ok().body(new HashMap<>() {{
                put("userinfo", null);
                put("message", "OK");
            }});
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "user")
    public ResponseEntity<?> getUserInfo(HttpSession session) {
        try {
            // 세션에 저장한 액세스 토큰을 매개로 유저 정보를 가져온다.
            HashMap<String, Object> userInfo = userService.getUserInfo((String)session.getAttribute("access_Token"));
            // 이메일을 통해 DB를 탐색하고 탐색한 유저의 정보를 전해준다.
            User user = userService.FindUserByEmail((String)userInfo.get("email"));
            // DB에 유저정보가 없다면, 아래와 같은 응답을 보내준다.
            if(user == null) {
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
    }

    @PatchMapping(value = "user")
    public ResponseEntity<?> ChangeUserInfo(@RequestBody(required = true) UserChangeInfoDTO userChangeInfoDTO, HttpSession session) {
        try {
            // 세션에 저장한 액세스 토큰을 매개로 유저 정보를 가져온다.
            HashMap<String, Object> userInfo = userService.getUserInfo((String)session.getAttribute("access_Token"));
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
