package com.example.worrybox.src.notice.api;

import com.example.worrybox.src.notice.api.dto.request.PostTokenReq;
import com.example.worrybox.src.notice.application.NoticeService;
import com.example.worrybox.src.user.api.dto.request.PostNameReq;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notices")
public class NoticeController {
    private final NoticeService noticeService;

    /* 중복 체크 API */
    @Operation(summary = "닉네임 중복 체크", description="닉네임 중복체크를 진행합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용 가능한 닉네임입니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
            @ApiResponse(responseCode = "4001", description = "중복된 이름입니다")
    })
    @PostMapping("/{userId}/token")
    public BaseResponse<String> nameCheck(@PathVariable Long userId, @Valid @RequestBody PostTokenReq postTokenReq) {
        try {
            return new BaseResponse<>(noticeService.getToken(userId, postTokenReq.getToken()));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }


}
