package com.example.worrybox.src.user.api.dto;

import lombok.Getter;

@Getter
public class WorryTime {
    String startTime;
    String endTime;

    public WorryTime(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
