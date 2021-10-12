package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.Authorization.CallBackAuthorization;
import com.Team4.FetchNoteServer.Domain.Authorization.CallBackToken;
import com.Team4.FetchNoteServer.Domain.Authorization.Token;
import com.Team4.FetchNoteServer.Domain.Authorization.UserData;
import com.Team4.FetchNoteServer.Domain.UserSignUp;
import com.Team4.FetchNoteServer.Entity.OAuthCode;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class OAuthController {

    private final UserService userService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public OAuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/oauth")
    public ResponseEntity<?> PostCallBack(@RequestParam("code") String code){
        try{
//            CallBackToken callBackToken = new CallBackToken();
//            // Authorization Code와 DB에 있는 User 데이터를 사용하여 토큰을 받아온다.
//            OAuthCode oac = userService.FindUserOAuth();
//            UserData userData = new UserData(oac.getClientId(), authorization.getAuthorizationCode()); // Git URL에 전달할 데이터 객체를 생성한다.
//
//            Token token = restTemplate.postForObject("https://kauth.kakao.com/oauth/token", userData, Token.class); //restTemplate을 사용하여 KaKaO URL에 post 요청을 보낸다.
//
//            if(token != null){
//                callBackToken.setAccessToken(token.getAccess_token());
//            }
            String access_Token = userService.getAccessToken(code);
            HashMap<String, Object> userInfo = userService.getUserInfo(access_Token);
            System.out.println("login Controller : " + userInfo);

            User user = userService.FindUserEmail((String)userInfo.get("email"));

            if(user == null) {
                UserSignUp userSignUp = new UserSignUp();
                userSignUp.setEmail((String)userInfo.get("email"));
                userSignUp.setNickname((String)userInfo.get("nickname"));
                userService.CreateUserData(userSignUp);
            }

            return ResponseEntity.ok().body(new HashMap<>() {{
                put("message", "OK");
            }});
        }catch (Exception error){
            return ResponseEntity.badRequest().body("Not found!");
        }
    }
}
