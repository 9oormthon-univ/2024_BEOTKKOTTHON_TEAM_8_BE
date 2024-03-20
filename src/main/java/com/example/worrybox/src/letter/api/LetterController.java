package com.example.worrybox.src.letter.api;

import com.example.worrybox.src.letter.api.dto.request.PostLetterReq;
import com.example.worrybox.src.letter.api.dto.response.GetArriveRes;
import com.example.worrybox.src.letter.api.dto.response.GetLettersRes;
import com.example.worrybox.src.letter.api.dto.response.PostLetterRes;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/letters")
public class LetterController {
    private final LetterService letterService;

    /* 오늘의 편지 존재 여부 반환 */
    @Operation(summary = "오늘의 편지 존재 여부 반환", description="오늘 나에게 온 편지가 있는지 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
            @ApiResponse(responseCode = "4000", description = "존재하지 않는 유저입니다.")
    })
    @GetMapping("/{userId}/existence")
    public BaseResponse<GetArriveRes> checkLetter(@PathVariable Long userId) {
        try {
            GetArriveRes arriveRes = new GetArriveRes(letterService.checkLetter(userId));
            return new BaseResponse<>(arriveRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /* 미래의 나에게로 보내는 편지 작성 */
    @Operation(summary = "미래의 나에게 보내는 편지 작성", description="미래의 나에게 보내는 편지를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
            @ApiResponse(responseCode = "4000", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "5000", description = "편지 도착 날짜는 오늘 날짜보다 이후여야 합니다.")
    })
    @PostMapping("/{userId}")
    public BaseResponse<PostLetterRes> sendLetter(@PathVariable Long userId, @Valid @RequestBody PostLetterReq postLetterReq) {
        try {
            if(!checkDate(postLetterReq.getArrivalDate())) {
                return new BaseResponse<>(BaseResponseStatus.LETTER_INVALID_DATE);
            }

            PostLetterRes letterRes = new PostLetterRes(letterService.sendLetter(userId, postLetterReq));
            return new BaseResponse<>(letterRes);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (ParseException e) {
            return new BaseResponse<>(BaseResponseStatus.INVALID_PARAMETERS);
        }
    }

    public boolean checkDate(String arrivalDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date arrival = formatter.parse(arrivalDate);
        Date now = new Date();

        return arrival.after(now);
    }

    /* 미래의 나에게로 보내는 편지 리스트 반환 */
    @Operation(summary = "미래의 나에게 보내는 편지 리스트 반환", description="미래의 나에게 보내는 편지 리스트를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
            @ApiResponse(responseCode = "4000", description = "존재하지 않는 유저입니다.")
    })
    @GetMapping("/{userId}")
    public BaseResponse<List<GetLettersRes>> sendLetter(@PathVariable Long userId) {
        try {
            return new BaseResponse<>(letterService.getLetter(userId));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
