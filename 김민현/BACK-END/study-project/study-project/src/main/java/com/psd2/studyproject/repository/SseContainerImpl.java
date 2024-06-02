package com.psd2.studyproject.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Getter
public class SseContainerImpl implements SseContainer{

    private final Map<String, List<SseEmitter>> groupEmitters = new ConcurrentHashMap<>();

    @Override
    public int getConnectCountById(String groupId) {
        List<SseEmitter> sseEmitters = groupEmitters.getOrDefault(groupId,new ArrayList<>());
        return sseEmitters.size();
    }
}
