package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/image")
    public void UploadImage() {
        imageRepository.ImageUpload();
    }

    @GetMapping("/image")
    public void DownloadImage() {
        imageRepository.ImageDownload();
    }

    @DeleteMapping("/image")
    public void DeleteImage() {
        imageRepository.ImageDelete();
    }
}
