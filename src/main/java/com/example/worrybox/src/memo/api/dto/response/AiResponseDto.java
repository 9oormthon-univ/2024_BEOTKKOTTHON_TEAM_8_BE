package com.example.gcptest.gpt.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AiResDto {
    private String question;
    private String answer;

//    public static AiResDto createAiResDto(AiResDto aiResDto){
//
//    }
}
