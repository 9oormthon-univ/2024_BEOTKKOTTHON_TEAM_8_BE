package com.example.worrybox.src.memo.api;

import com.example.worrybox.src.memo.api.dto.request.MemoRequestDto;
import com.example.worrybox.src.memo.api.dto.response.MemoResponseDto;
import com.example.worrybox.src.memo.application.MemoService;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponse;
import com.example.worrybox.utils.config.BaseResponseStatus;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "걱정 메모")
@RequestMapping("")
public class MemoController {
    private final MemoService memoService;

    // 메모 작성
    @Operation(summary = "걱정 메모 작성", description="걱정 메모를 작성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메모 작성을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/memos/{userId}")
    public BaseResponse<String> writeMemo(@RequestBody MemoRequestDto memoRequestDto, @PathVariable Long userId) {
        try {
            String memo = memoRequestDto.getWorryText();

            // 메모가 제대로 들어왔는지 검사
            BaseResponseStatus status = isJoinValid(memo);
            if(status != BaseResponseStatus.SUCCESS) {
                return new BaseResponse<>(status);
            }

            // 제대로 들어왔다면 다음 진행
            return new BaseResponse<>(memoService.writeMemo(userId,memoRequestDto));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    public BaseResponseStatus isJoinValid(String memo) {
        if(memo.isBlank()) return BaseResponseStatus.JOIN_EMPTY_NAME;  // 메모가 비어있는지 확인

        return BaseResponseStatus.SUCCESS;
    }

    // 메모 조회
    @Operation(summary = "걱정 메모 조회", description="걱정 메모를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메모 조회를 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/memos/{userId}")
    public BaseResponse<List<MemoResponseDto>> Memo(@PathVariable Long userId)  {
        try{
            return new BaseResponse<>(memoService.memoList(userId));
        } catch (Exception e){
            BaseResponseStatus status = BaseResponseStatus.INVALID_USER;
            return new BaseResponse<>(status);
        }
    }
}
