package com.exam.ingsw.dietideals24.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RetrieveByteArrayController {
    static byte[] imageContent;

    @PostMapping("/uploadImageContent")
    public ResponseEntity<String> uploadImageContent(@RequestBody byte[] imageData) {
        imageContent = imageData; // TODO: MUST CHECK IF IT IS NOT NULL (IF NULL TRY ANOTHER METHOD TO SAVE CONTENT)
        return new ResponseEntity<>("Item's image content retrieved successfully!", HttpStatus.OK);
    }
}