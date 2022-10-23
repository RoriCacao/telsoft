package ru.sultanov.telsoft.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sultanov.telsoft.entity.ImageModel;
import ru.sultanov.telsoft.payload.response.MessageResponse;
import ru.sultanov.telsoft.services.ImageUploadService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageUploadController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageUploadController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    //Загрузка изображения пользователя
    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file,
                                                             Principal principal) throws IOException {

        imageUploadService.uploadImageToUser(file, principal);
        return new ResponseEntity<>(new MessageResponse("Image uploaded successfully"), HttpStatus.OK);
    }

    //Получение изображения пользователя
    @GetMapping("/profileImage")
    public ResponseEntity<byte[]> getImageForUser(Principal principal) {
        ImageModel imageModel = imageUploadService.getImageToUser(principal);
        byte[] result = imageModel.getImageBytes();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
