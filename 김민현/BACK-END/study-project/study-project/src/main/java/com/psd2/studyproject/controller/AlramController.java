package com.psd2.studyproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@RestController
public class AlramController {
    private SimpMessagingTemplate messagingTemplate;
    private Map<String, List<SseEmitter>> groupEmitters = new ConcurrentHashMap<>();


    @PostMapping("/api/articles")
    public ResponseEntity<Map<String, String>> addArticle(@RequestBody Map<String, String> request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(request);
    }

    @GetMapping("/alerts/{groupId}")
    public SseEmitter subscribeToGroupAlerts(@PathVariable String groupId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        groupEmitters.computeIfAbsent(groupId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> groupEmitters.get(groupId).remove(emitter));
        emitter.onTimeout(() -> groupEmitters.get(groupId).remove(emitter));
        emitter.onError(e -> groupEmitters.get(groupId).remove(emitter));

        return emitter;
    }

    @GetMapping("api/alerts")
    public void sendAlertToGroup(@RequestParam String groupId) {
        List<SseEmitter> emitters = groupEmitters.getOrDefault(groupId, Collections.emptyList());
        List<SseEmitter> deadEmitters = new ArrayList<>();
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().data("message"));
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        });
        emitters.removeAll(deadEmitters);
    }
}
