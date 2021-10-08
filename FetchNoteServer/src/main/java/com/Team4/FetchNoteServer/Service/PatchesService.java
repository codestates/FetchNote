package com.Team4.FetchNoteServer.Service;

import com.Team4.FetchNoteServer.Repository.PatchesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatchesService {

    private final PatchesRepository patchesRepository;

    @Autowired
    public PatchesService(PatchesRepository patchesRepository) {
        this.patchesRepository = patchesRepository;
    }
}
