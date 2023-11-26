package com.psd2.studyproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AlramController {
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/api/articles")
    public ResponseEntity<Map<String, String>> addArticle(@RequestBody Map<String,String> request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(request);
    }
}
