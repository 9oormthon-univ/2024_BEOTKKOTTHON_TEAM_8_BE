package com.example.worrybox.src.letter.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public interface GetLettersRes {
    @Schema(description = "편지 아이디", example = "1")
    Long getLetterId();
    @Schema(description = "유저 아이디", example = "1")
    Long getUserId();
    @Schema(description = "편지 내용", example = "미래의 나 힘내라")
    String getLetter();
    @Schema(description = "편지 전송 날짜", example = "2024-03-14")
    String getSendDate();
    @Schema(description = "편지 도착 날짜", example = "2024-03-16")
    String getArrivalDate();
}
