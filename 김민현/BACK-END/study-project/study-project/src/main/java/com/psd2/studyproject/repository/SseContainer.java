package com.psd2.studyproject.repository;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

public interface SseContainer {

    public Map<String, List<SseEmitter>> getGroupEmitters();

    public int getConnectCountById(String groupId);

}
