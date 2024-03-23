package com.example.worrybox.src.memo.api.dto.response;

import com.example.worrybox.src.memo.domain.Memo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemoResponseDto {

    @Schema(description = "걱정 메모 내용", example = "너무 힘들어요.")
    @NotNull
    private String worryText;

    @Schema(description = "걱정 메모 해결책", example = "뭐뭐 때문인 거 같다. 이렇게 해야겠다!")
    @NotNull
    private String solution;

    @NotNull
    private Timestamp createAt;

    @NotNull
    private Timestamp updateAt;

    @Schema(description = "메모 아이디", example = "1")
    @NotNull
    private Long memoId;


    public static MemoResponseDto from(Memo memo){
        return MemoResponseDto.builder()
                .worryText(memo.getWorryText())
                .solution(memo.getSolution())
                .updateAt(memo.getUpdatedAt())
                .createAt(memo.getUpdatedAt())
                .memoId(memo.getId())
                .build();
    }
}
