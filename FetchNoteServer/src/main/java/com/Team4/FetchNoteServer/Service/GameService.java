package com.Team4.FetchNoteServer.Service;

import com.Team4.FetchNoteServer.Entity.Game;
import com.Team4.FetchNoteServer.Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> GetAllGame (){
        return gameRepository.FindAll();
    }

    public List<Game> GetGameByUserId (long id){
        return gameRepository.FindByUserId(id);
    }
}
