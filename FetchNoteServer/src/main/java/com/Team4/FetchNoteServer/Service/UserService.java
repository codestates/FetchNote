package com.Team4.FetchNoteServer.Service;

import com.Team4.FetchNoteServer.Domain.UserLogin;
import com.Team4.FetchNoteServer.Domain.UserSignUp;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;

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
}
