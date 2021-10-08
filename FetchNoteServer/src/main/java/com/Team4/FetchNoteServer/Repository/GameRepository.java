package com.Team4.FetchNoteServer.Repository;

import com.Team4.FetchNoteServer.Domain.GameDTO;
import com.Team4.FetchNoteServer.Entity.Game;
import com.Team4.FetchNoteServer.Entity.LikeGame;
import com.Team4.FetchNoteServer.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class GameRepository {

    private final EntityManager entityManager;

    @Autowired
    public GameRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Game> FindAll(){
        return entityManager
                .createQuery("SELECT a FROM Game a", Game.class)
                .getResultList();
    }

    public List<Game> FindByUserId(long id){
        List<Game> result = new ArrayList<>();

        List<LikeGame> likeGame = entityManager
                .createQuery("SELECT games FROM LikeGame games WHERE user_id =" + id + "",LikeGame.class)
                .getResultList();

        for(LikeGame el : likeGame) result.add(el.getGame());

        return result;
    }
}
