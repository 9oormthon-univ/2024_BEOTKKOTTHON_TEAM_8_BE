package com.example.worrybox.src.letter.api;

import com.example.worrybox.src.letter.api.dto.request.PostLetterReq;
import com.example.worrybox.src.letter.application.LetterService;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponse;
import com.example.worrybox.utils.config.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letters")
public class LetterController {
    private final LetterService letterService;

    /* 미래의 나에게로 보내는 편지 작성 */
    @Operation(summary = "미래의 나에게 보내는 편지 작성", description="미래의 나에게 보내는 편지를 작성합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
            @ApiResponse(responseCode = "4000", description = "존재하지 않는 유저입니다.")
    })
    @PostMapping("/{userId}")
    public BaseResponse<Long> sendLetter(@PathVariable Long userId, @Valid @RequestBody PostLetterReq postLetterReq) {
        try {
            return new BaseResponse<>(letterService.sendLetter(userId, postLetterReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (ParseException e) {
            return new BaseResponse<>(BaseResponseStatus.INVALID_PARAMETERS);
        }
    }

//    /* 미래의 나에게로 보내는 편지 리스트 반환 */
//    @Operation(summary = "미래의 나에게 보내는 편지 리스트 반환", description="미래의 나에게 보내는 편지 리스트를 반환합니다")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다"),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
//            @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
//                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
//            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
//            @ApiResponse(responseCode = "4000", description = "존재하지 않는 유저입니다.")
//    })
//    @GetMapping("/{userId}")
//    public BaseResponse<Long> sendLetter(@PathVariable Long userId) {
//        try {
//            return new BaseResponse<>(letterService.getLetter(userId));
//        } catch (BaseException e) {
//            return new BaseResponse<>(e.getStatus());
//        }
//    }
}
