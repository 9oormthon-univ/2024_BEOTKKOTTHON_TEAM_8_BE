package com.example.worrybox.src.memo.api.dto.response;

import com.example.worrybox.src.memo.api.dto.request.MemoRequestDto;
import com.example.worrybox.src.memo.domain.Memo;
import com.example.worrybox.src.user.domain.User;
import com.example.worrybox.utils.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoResponseDto {

    @NotNull
    private String worryText;

    @NotNull
    private String solution;

    @NotNull
    private Timestamp updateAt;

    @NotNull
    private Long memoId;


    public static MemoResponseDto from(Memo memo){
        return MemoResponseDto.builder()
                .worryText(memo.getWorryText())
                .solution(memo.getSolution())
                .updateAt(memo.getUpdatedAt())
                .memoId(memo.getId())
                .build();
    }
}
