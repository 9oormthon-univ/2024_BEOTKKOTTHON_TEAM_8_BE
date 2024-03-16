package com.example.worrybox.src.letter.api.dto.response;

import lombok.Getter;

public interface GetLettersRes {
    Long getLetterId();
    Long getUserId();
    String getLetter();
    String getSendDate();
    String getArrivalDate();
}
