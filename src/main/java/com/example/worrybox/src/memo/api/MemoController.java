package com.example.worrybox.src.memo.api;

import com.example.worrybox.src.memo.api.dto.request.AiRequestDto;
import com.example.worrybox.src.memo.api.dto.request.MemoRequestDto;
import com.example.worrybox.src.memo.api.dto.response.AiResponseDto;
import com.example.worrybox.src.memo.api.dto.response.MemoResponseDto;
import com.example.worrybox.src.memo.api.dto.response.TimeResponseDto;
import com.example.worrybox.src.memo.application.MemoService;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponse;
import com.example.worrybox.utils.config.BaseResponseStatus;
import com.example.worrybox.utils.entity.Status;
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
@RequestMapping("/memos")
public class MemoController {
    private final MemoService memoService;

    // 메모 작성
    @Operation(summary = "걱정 메모 작성", description = "걱정 메모를 작성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메모 작성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/{userId}")
    public BaseResponse<String> writeMemo(@RequestBody @Validated MemoRequestDto memoRequestDto, @PathVariable Long userId) {
        try {
            String memo = memoRequestDto.getWorryText();

            // 메모가 제대로 들어왔는지 검사
            BaseResponseStatus status = isJoinValid(memo);
            if (status != BaseResponseStatus.SUCCESS) {
                return new BaseResponse<>(status);
            }

            // 제대로 들어왔다면 다음 진행
            return new BaseResponse<>(memoService.writeMemo(userId, memoRequestDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    public BaseResponseStatus isJoinValid(String memo) {
        if (memo.isBlank()) return BaseResponseStatus.INVALID_MEMO;  // 메모가 비어있는지 확인

        return BaseResponseStatus.SUCCESS;
    }

    // 메모 조회
    @Operation(summary = "걱정 메모 조회", description = "걱정 메모를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메모 조회를 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/{userId}")
    public BaseResponse<List<MemoResponseDto>> Memo(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(memoService.memoList(userId));
        } catch (Exception e) {
            BaseResponseStatus status = BaseResponseStatus.INVALID_USER;
            return new BaseResponse<>(status);
        }
    }

    // 메모 삭제
    @Operation(summary = "걱정 메모 삭제", description = "걱정 메모를 삭제합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메모 삭제를 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PatchMapping("/{userId}")
    public BaseResponse<Status> deleteMemo(@PathVariable Long userId){
        try {
            return new BaseResponse<>(memoService.deleteMemo(userId));
        }
        catch (Exception e){
            BaseResponseStatus status = BaseResponseStatus.INVALID_MEMO;
            return new BaseResponse<>(status);
        }
    }

    // AI 응답
    @Operation(summary = "AI 응답", description = "AI에게 물어봅니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "응답 생성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/ai")
    public BaseResponse<AiResponseDto> advice(@RequestBody @Validated AiRequestDto aiRequestDto){
        try{
            return new BaseResponse<>(memoService.askForAdvice(aiRequestDto));
        }
        catch (Exception e){
            BaseResponseStatus status = BaseResponseStatus.RESPONSE_FAILURE;
            return new BaseResponse<>(status);
        }
    }

    // 시간 늘려주기
    @Operation(summary = "시간 늘려주기", description = "걱정 시간을 늘려줍니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "걱정 시간 늘리기를 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/{memoId}/time-setting")
    public BaseResponse<TimeResponseDto> extendTimeSetting(@PathVariable Long memoId){
        try{
            return new BaseResponse<>(memoService.extendTime(memoId));
        }
        catch (Exception e){
            BaseResponseStatus status = BaseResponseStatus.FAILED_EXTEND_TIME;
            return new BaseResponse<>(status);
        }
    }
}
