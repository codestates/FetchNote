package com.Team4.FetchNoteServer.Controller;

import com.Team4.FetchNoteServer.Domain.ImageDTO;
import com.Team4.FetchNoteServer.Repository.ImageRepository;
import com.Team4.FetchNoteServer.Service.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class ImageController {

    private final ImageRepository imageRepository;
    private final S3Uploader s3Uploader;

    @Autowired
    public ImageController(ImageRepository imageRepository, S3Uploader s3Uploader) {
        this.imageRepository = imageRepository;
        this.s3Uploader = s3Uploader;
    }

    private final static String S3_DIR = "static";

    @PostMapping("/image")
    public ResponseEntity<?> UploadImage(HttpServletRequest data,
                                      @RequestPart(value = "imageblob") MultipartFile imageblob){

        try {
            ImageDTO dto = new ImageDTO();
            dto.setPatchId(Long.parseLong(data.getParameter("patchId")));
            dto.setImagename(data.getParameter("imagename"));
            dto.setImagetype(data.getParameter("imagetype"));

            String fileName = UUID.randomUUID() + "-" + dto.getImagename();
            String url = s3Uploader.upload(imageblob, S3_DIR, fileName);

            dto.setAddress(url);
            imageRepository.ImageUpload(dto);

            return ResponseEntity.ok().body(
                    new HashMap<>(){
                        {
                            put("message", "ok");
                            put("URL", url);
                        }
                    });
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/image")
    public ResponseEntity<?> DownloadImage(@RequestParam long id) {
        try {
            String address = imageRepository.ImageDownload(id);

            return ResponseEntity.ok().body(
                    new HashMap<>(){
                        {
                            put("message", "ok");
                            put("URL", address);
                        }
                    });
        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @DeleteMapping("/image")
    public ResponseEntity<?> DeleteImage(@RequestBody ImageDTO data) {
        String address = imageRepository.ImageDelete(data.getImageId());
        String key = S3_DIR + "/" + address.substring(address.lastIndexOf("/") + 1);
        s3Uploader.delete(key);
        return ResponseEntity.ok().body(new HashMap<>(){{put("message", "ok");}});
    }
}
