package com.psd2.studyproject.controller;

import com.psd2.studyproject.service.AlarmService;
import lombok.RequiredArgsConstructor;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final AlarmService alarmService;
    private SimpMessagingTemplate messagingTemplate;

//
//    @PostMapping("/api/articles")
//    public ResponseEntity<Map<String, String>> addArticle(@RequestBody Map<String, String> request) {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(request);
//    }


    //SSE 구독
    @GetMapping("/alerts/{groupId}")
    public SseEmitter subscribeToGroupAlerts(@PathVariable String groupId) {
        return alarmService.subscribeByChannel(groupId);

    }

    // SSE 메세지 보내기
    @GetMapping("api/alerts")
    public void sendAlertToGroup(@RequestParam String groupId) {
        alarmService.sendAlarm(groupId);
    }
}
