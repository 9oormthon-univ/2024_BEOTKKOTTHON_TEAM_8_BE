package com.example.worrybox.src.memo.api.dto.response;

import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TimeResponseDto {
    private Timestamp updatedAt;

    public void updateTimestamp() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
