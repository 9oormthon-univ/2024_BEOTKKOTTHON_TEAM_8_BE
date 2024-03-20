package com.example.worrybox.src.user.api;

import com.example.worrybox.src.user.api.dto.WorryTime;
import com.example.worrybox.src.user.api.dto.request.PostJoinReq;
import com.example.worrybox.src.user.api.dto.request.PostLoginReq;
import com.example.worrybox.src.user.api.dto.request.PostNameReq;
import com.example.worrybox.src.user.api.dto.response.PostLoginRes;
import com.example.worrybox.src.user.api.dto.response.PostUserRes;
import com.example.worrybox.src.user.application.UserService;
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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /* 중복 체크 API */
    @Operation(summary = "닉네임 중복 체크", description="닉네임 중복체크를 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
            @ApiResponse(responseCode = "4001", description = "중복된 이름입니다.")
    })
    @PostMapping("/name-check")
    public BaseResponse<String> nameCheck(@Valid @RequestBody PostNameReq postNameReq) {
        try {
            return new BaseResponse<>(userService.nameCheck(postNameReq.getName()));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /* 가입 API */
    @Operation(summary = "회원가입", description="회원가입을 진행합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
        @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
        @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
                content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
        @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
        @ApiResponse(responseCode = "4001", description = "중복된 이름입니다"),
        @ApiResponse(responseCode = "4002", description = "비밀번호는 4자리여야 합니다."),
//        @ApiResponse(responseCode = "4004", description = "걱정 시작 시간은 걱정 마감 시간보다 빨라야합니다.")
    })
    @PostMapping("/join")
    public BaseResponse<PostUserRes> join(@Valid @RequestBody PostJoinReq postJoinReq) {
        try {
            String name = postJoinReq.getName();
            int password = postJoinReq.getPassword();

            // 이름, 비밀번호 제대로 들어왔는지 검사
            BaseResponseStatus status = isJoinValid(name, password);
            if(status != BaseResponseStatus.SUCCESS) {
                return new BaseResponse<>(status);
            }

            WorryTime worryTime = timeValid(postJoinReq.getStartTime(), postJoinReq.getEndTime());
            postJoinReq.setStartTime(worryTime.getStartTime());
            postJoinReq.setEndTime(worryTime.getEndTime());

            // 제대로 들어왔다면 다음 진행
            return new BaseResponse<>(userService.join(postJoinReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    public WorryTime timeValid(String startTime, String endTime) {
        // DateTimeFormatter 정의 (AM/PM 포함)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:ma", Locale.US);

        // 문자열 파싱
        LocalTime start = LocalTime.parse(startTime, formatter);
        LocalTime end = LocalTime.parse(endTime, formatter);

//        System.out.println("시작 시간: " + start);
//        System.out.println("끝 시간: " + end);

        return new WorryTime(start.toString(), end.toString());
    }

    /* 가입 API */
    @Operation(summary = "로그인", description="로그인을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청이 성공적으로 처리되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                    content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
            @ApiResponse(responseCode = "4000", description = "존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "4002", description = "비밀번호는 4자리여야 합니다."),
    })
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> login(@Valid @RequestBody PostLoginReq postLoginReq) {
        try {
            String name = postLoginReq.getName();
            int password = postLoginReq.getPassword();

            // 이름, 비밀번호 제대로 들어왔는지 검사
            BaseResponseStatus status = isJoinValid(name, password);
            if(status != BaseResponseStatus.SUCCESS) {
                return new BaseResponse<>(status);
            }

            // 제대로 들어왔다면 다음 진행
            return new BaseResponse<>(userService.login(postLoginReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    public BaseResponseStatus isJoinValid(String name, int password) {
        if((int)(Math.log10(password) + 1) != 4) return BaseResponseStatus.JOIN_INVALID_PASSWORD;
        return BaseResponseStatus.SUCCESS;
    }

}
