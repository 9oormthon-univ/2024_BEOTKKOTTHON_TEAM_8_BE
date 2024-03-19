package com.example.worrybox.src.notice.api.dto.request;

import lombok.Getter;

@Getter
public class NotificationRequest {
    private String token;
    private String title;
    private String body;

    public NotificationRequest(String token, String title, String body) {
        this.token = token;
        this.title = title;
        this.body = body;
    }
}
