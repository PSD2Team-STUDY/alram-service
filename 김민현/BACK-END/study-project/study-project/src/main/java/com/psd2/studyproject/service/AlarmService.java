package com.psd2.studyproject.service;

import com.psd2.studyproject.repository.SseContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlarmService {

    private final SseContainer sseContainer;


    public SseEmitter subscribeByChannel ( String groupId){
      log.info(groupId + " Count : " + sseContainer.getConnectCountById(groupId));
        Map<String, List<SseEmitter>>  groupEmitters = sseContainer.getGroupEmitters();
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        groupEmitters.computeIfAbsent(groupId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> groupEmitters.get(groupId).remove(emitter));
        emitter.onTimeout(() -> groupEmitters.get(groupId).remove(emitter));
        emitter.onError(e -> groupEmitters.get(groupId).remove(emitter));
        return emitter;
    }

    public void sendAlarm(String groupId){
        Map<String, List<SseEmitter>>  groupEmitters = sseContainer.getGroupEmitters();
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
