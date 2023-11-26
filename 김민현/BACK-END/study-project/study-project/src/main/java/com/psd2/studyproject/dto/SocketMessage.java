package com.psd2.studyproject.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Map;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SocketMessage implements Serializable {

    private String type;
    private String rtcSession;
    private String userId;
    private String message;
    private Map<String,String> messageData;
}