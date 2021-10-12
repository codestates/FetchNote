package com.Team4.FetchNoteServer.Service;

import com.Team4.FetchNoteServer.Domain.UserLogin;
import com.Team4.FetchNoteServer.Domain.UserSignUp;
import com.Team4.FetchNoteServer.Entity.OAuthCode;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Repository.UserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private static long GET_ID = 0L;
    private final static String SIGN_KEY = "givemeajob";
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public User FindUserData(UserLogin userLogin) {
        List<User> user = userRepository.FindByEmail(userLogin.getEmail());
        if(user == null) return null;

        return user.get(0);
    }

    public User FindUserEmail(String email) {
        List<User> list = userRepository.FindByEmail(email);
        if(list.size() == 0) return null;
        return list.get(0);
    }

    public User CreateUserData(UserSignUp userSignUp) {
        for(User u : userRepository.FindUserList()) {
            if(u.getEmail().equals(userSignUp.getEmail())) {
                return null;
            }
        }
        GET_ID++;
        userRepository.CreateUser(userSignUp, GET_ID);
        return userRepository.FindById(GET_ID);
    }

    public String CreateToken(User user) {
        // 토큰생성
        // 토큰에 "username", "email"dl ekarlsek.
        // 토큰의 유효시간은 2시간이다.
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofSeconds(1000 * 60 * 60 * 2).toMillis()))
                .claim("nickname", user.getNickname())
                .claim("email", user.getEmail())
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
                .compact();
    }

    public Map<String, String> CheckToken(String key) {
        // / 매개변수 key를 통해 전달 되는 토큰 값에 유효성을 체크하여 결과를 리턴한다.
        try {
            Claims claims = Jwts.parser().setSigningKey(SIGN_KEY)
                    .parseClaimsJws(key)
                    .getBody();

            String userEmail = (String) claims.get("email");
            return new HashMap<>() {{
                put("email", userEmail);
                put("message", "ok");
            }};
        } catch (ExpiredJwtException e) {
            return new HashMap<>() {{
                put("email", null);
                put("message", "The token time has expired.");
            }};
        } catch (JwtException e) {
            return new HashMap<>() {{
                put("email", null);
                put("message", "The token is invalid.");
            }};
        }
    }

    // 헤더에 "token "이 포함 되어 있는지 체크한다.
    public void validationAuthorizationHeader(String header) {
        if (header == null || !header.startsWith("token ")) {
            throw new IllegalArgumentException();
        }
    }
    // 헤더에 "token "을 제거한다.
    public String extractToken(String authorizationHeader) {
        return authorizationHeader.substring("token ".length());
    }

    public OAuthCode FindUserOAuth() {
        return userRepository.FindUserOAuthCode();
    }

    public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=b1439c1ca8b3431678a1c6cc28df99c6");
            sb.append("&redirect_uri=https://localhost:8080/oauth");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return access_Token;
    }

    public HashMap<String, Object> getUserInfo (String access_Token) {
        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakao_account.getAsJsonObject().get("email").getAsString();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return userInfo;
    }
}
