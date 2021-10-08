package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.UserLogin;
import com.Team4.FetchNoteServer.Domain.UserSignUp;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("https://localhost:3000")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/signup")
    public ResponseEntity<?> UserSignUp(@RequestBody(required = true)UserSignUp userSignUp, HttpServletResponse response) {
        boolean flag = true;
        if(userSignUp.getEmail() == null || userSignUp.getNickname() == null) flag = false;
        // userSignUp 객체 내에 기입하지 않은 경우
        if(!flag) {
            return ResponseEntity.badRequest().body(new HashMap<>(){{
                put("userinfo", null);
                put("message", "insufficient parameters supplied");
            }});
        }

        User user = userService.CreateUserData(userSignUp);
        // 같은 email이 존재하는 경우
        if(user == null) {
            return ResponseEntity.ok().body(new HashMap<>(){{
                put("userinfo", null);
                put("message", "This email already exists.");
            }});
        }

        Cookie cookie = new Cookie("token", userService.CreateToken(user));
        response.addCookie(cookie);

        return ResponseEntity.ok().body(new HashMap<>() {{
            put("userinfo", new HashMap<>() {{
                put("id", user.getId());
                put("email", user.getEmail());
                put("nickname", user.getNickname());
                put("exp", user.getExp());
            }});
            put("message", "OK");
        }});
    }

    @PostMapping(value = "/user/login")
    public ResponseEntity<?> UserLogin(@RequestBody(required = true)UserLogin userLogin, HttpServletResponse response) {
        try {
            // DB에 저장된 유저정보를 확인해 토큰을 발행하는 메소드
            // 매개변수로 전달된 정보를 통해 DB > User 테이블에 유저 정보와 비교후 유효한 유저일 경우 토큰을 생성하여 쿠키를 통해 클라이언트에 전달한다.
            User user = userService.FindUserData(userLogin);
            if(user == null) {
                return ResponseEntity.badRequest().body(new HashMap<>() {{
                    put("userinfo", null);
                    put("message", "Invalid user or Wrong password");
                }});
            }

            Cookie cookie = new Cookie("token", userService.CreateToken(user));
            response.addCookie(cookie);

            return ResponseEntity.ok().body(new HashMap<>() {{
                put("userinfo", new HashMap<>() {{
                    put("id", user.getId());
                    put("email", user.getEmail());
                    put("nickname", user.getNickname());
                }});
                put("message", "OK");
            }});
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("err");
        }
    }

    @PostMapping(value = "/user/logout")
    public ResponseEntity<?> UserLogOut(HttpServletResponse response) {
        try {
            // 유저 로그아웃 기능을 수행하는 메소드
            // 해당 요청 시, 클라이언트가 가진 token 키 값을 가진 쿠키가 제거된다
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);

            return ResponseEntity.ok().body(new HashMap<>() {{
                put("userinfo", null);
                put("message", "OK");
            }});
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("err");
        }
    }

    @GetMapping(value = "/user/info")
    public ResponseEntity<?> getUserInfo(@RequestParam String email) {
        try {
            // 현재 유저 정보를 불어오는 메소드
            User user = userService.FindUserEmail(email);
            if(user == null) {
                return ResponseEntity.badRequest().body(new HashMap<>() {{
                    put("userinfo", null);
                    put("message", "The user does not exist.");
                }});
            }

            return ResponseEntity.ok().body(new HashMap<>() {{
                put("userinfo", new HashMap<>() {{
                    put("id", user.getId());
                    put("email", user.getEmail());
                    put("nickname", user.getNickname());
                    put("exp", user.getExp());
                }});
                put("message", "OK");
            }});
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("err");
        }
    }
}
