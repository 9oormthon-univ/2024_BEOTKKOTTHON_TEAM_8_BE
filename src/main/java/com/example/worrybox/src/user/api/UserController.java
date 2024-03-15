package com.example.worrybox.src.user.api;

import com.example.worrybox.src.user.api.dto.request.PostJoinReq;
import com.example.worrybox.src.user.api.dto.request.PostTimeReq;
import com.example.worrybox.src.user.api.dto.response.PostJoinRes;
import com.example.worrybox.src.user.application.UserService;
import com.example.worrybox.utils.config.BaseException;
import com.example.worrybox.utils.config.BaseResponse;
import com.example.worrybox.utils.config.BaseResponseStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@Api(tags = "회원가입 및 로그인")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /* 가입 API */
    @Operation(summary = "회원가입/로그인", description="회원가입 및 로그인을 진행합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입/로그인을 성공했습니다"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
        @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
                content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
        @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다."),
        @ApiResponse(responseCode = "4001", description = "중복된 이름입니다"),
        @ApiResponse(responseCode = "4002", description = "비밀번호는 6자리여야 합니다")
    })
    @PostMapping("/join")
    public BaseResponse<PostJoinRes> join(@RequestBody PostJoinReq postJoinReq) {
        try {
            String name = postJoinReq.getName();
            int password = postJoinReq.getPassword();

            // 이름, 비밀번호 제대로 들어왔는지 검사
            BaseResponseStatus status = isJoinValid(name, password);
            if(status != BaseResponseStatus.SUCCESS) {
                return new BaseResponse<>(status);
            }

            // 제대로 들어왔다면 다음 진행
            return new BaseResponse<>(userService.join(postJoinReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    public BaseResponseStatus isJoinValid(String name, int password) {
        if((int)( Math.log10(password) + 1) != 6) return BaseResponseStatus.JOIN_INVALID_PASSWORD;
        return BaseResponseStatus.SUCCESS;
    }

    /* 걱정 시간 입력 API */
    @Operation(summary = "걱정 시간 입력", description="걱정 시간을 설정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입/로그인을 성공했습니다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치",
                content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN"))),
            @ApiResponse(responseCode = "400", description = "입력값이 잘못되었습니다.")
    })
    @PostMapping("/{userId}/time-setting")
    public BaseResponse<Long> timeSetting(@PathVariable Long userId, @Valid @RequestBody PostTimeReq postTimeReq) {
        try {
            // 제대로 들어왔다면 다음 진행
            return new BaseResponse<>(userService.timeSetting(userId, postTimeReq));
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}
