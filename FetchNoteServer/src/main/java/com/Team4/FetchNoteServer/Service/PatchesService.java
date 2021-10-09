package com.Team4.FetchNoteServer.Service;

import com.Team4.FetchNoteServer.Domain.PatchesDTO;
import com.Team4.FetchNoteServer.Entity.Game;
import com.Team4.FetchNoteServer.Entity.Patches;
import com.Team4.FetchNoteServer.Entity.User;
import com.Team4.FetchNoteServer.Repository.PatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatchesService {

    private final PatchesRepository patchesRepository;

    @Autowired
    public PatchesService(PatchesRepository patchesRepository) {
        this.patchesRepository = patchesRepository;
    }

    public List<Patches> GetAllPatches (long gameId) {
        return patchesRepository.FindByGameId(gameId);
    }

    public Patches GetPatches (long patchesId) {
        return patchesRepository.FindByPatchesId(patchesId);
    }

    public void RegisterPatches (User user, Game game, PatchesDTO data){
        patchesRepository.RegistPatches(user, game, data);
    }
}
