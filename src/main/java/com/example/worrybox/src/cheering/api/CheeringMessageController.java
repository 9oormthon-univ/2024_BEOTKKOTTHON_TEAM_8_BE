package com.example.worrybox.src.cheering.api;

import com.example.worrybox.src.cheering.api.dto.request.CheeringMessageRequestDto;
import com.example.worrybox.src.cheering.api.dto.response.CheeringMessageResponseDto;
import com.example.worrybox.src.cheering.application.CheeringMessageService;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponse;
import com.example.worrybox.utils.config.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cheerings")
public class CheeringMessageController {
    private final CheeringMessageService cheeringMessageService;

    // 응원 메세지 작성
    @Operation(summary = "응원 메세지 작성", description = "응원 메세지를 작성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응원 메세지 작성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/{userId}")
    public BaseResponse<String> sendMessage(
            @RequestBody @Validated CheeringMessageRequestDto cheeringMessageRequestDto,
            @PathVariable Long userId) {
        try {
            String message = cheeringMessageRequestDto.getCheeringText();

            // 메세지가 제대로 들어왔는지 검사
            BaseResponseStatus status = isJoinValid(message);
            if (status != BaseResponseStatus.SUCCESS) {
                return new BaseResponse<>(status);
            }

            // 제대로 들어왔다면 다음 진행
            return new BaseResponse<>(cheeringMessageService.sendMessage(userId, cheeringMessageRequestDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    public BaseResponseStatus isJoinValid(String memo) {
        if (memo.isBlank()) return BaseResponseStatus.INVALID_MESSAGE;  // 메세지가 비어있는지 확인

        return BaseResponseStatus.SUCCESS;
    }

    // 응원 메세지 조회
    @Operation(summary = "응원 메세지 조회", description = "응원 메세지를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응원 메세지 조회를 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("")
    public BaseResponse<List<CheeringMessageResponseDto>> messageList() {
        try {
            return new BaseResponse<>(cheeringMessageService.cheeringMessageList());
        } catch (Exception e) {
            BaseResponseStatus status = BaseResponseStatus.INVALID_USER;
            return new BaseResponse<>(status);
        }
    }
}
