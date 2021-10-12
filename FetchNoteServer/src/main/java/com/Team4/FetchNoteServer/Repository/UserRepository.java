package com.Team4.FetchNoteServer.Repository;

import com.Team4.FetchNoteServer.Domain.UserSignUp;
import com.Team4.FetchNoteServer.Entity.OAuthCode;
import com.Team4.FetchNoteServer.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class UserRepository {
    private final EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {this.entityManager = entityManager;}
    // DB User 테이블에 모든 유저 정보를 리턴한다.
    public List<User> FindUserList() {
        return entityManager.createQuery("SELECT u FROM User as u",  User.class).getResultList();
    }

    // DB User 테이블에 매개변수 email과 일치하는 유저 정보를 리턴한다.
    public List<User> FindByEmail(String email) {
//        return entityManager.createQuery("SELECT e FROM User e where e.email = '"+email+"'", User.class).getResultList();
        List<User> email_list = new ArrayList<>();
        List<User> list = FindUserList();

        for(User u : list) {
            if(u.getEmail().equals(email)) {
                email_list.add(u);
                break;
            }
        }
        return email_list;
    }

    // DB User 테이블에 매개변수 id와 일치하는 유저 정보를 리턴한다.
    public User FindById(long id) {
        return entityManager.find(User.class, id);
    }
    // DB User 테이블에 매개변수 userSignUp과 id의 데이터를 사용하여 유저 정보를 저장한다.
    public void CreateUser(UserSignUp userSignUp, Long id) {
        User user = new User();
        user.setId(id);
        user.setEmail(userSignUp.getEmail());
        user.setNickname(userSignUp.getNickname());
        entityManager.persist(user);

        entityManager.flush();
        entityManager.close();
    }

    public OAuthCode FindUserOAuthCode(){
        return entityManager.find(OAuthCode.class, 0L);
    }
}
