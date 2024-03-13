package com.example.worrybox.src.user.api;

import com.example.worrybox.src.user.api.dto.request.PostJoinReq;
import com.example.worrybox.src.user.application.UserService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "회원가입 및 로그인")
@RequestMapping("")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원가입/로그인", description="회원가입 및 로그인을 진행합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "회원가입/로그인을 성공했습니다"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
        @ApiResponse(responseCode = "400", description = "헤더 없음 or 토큰 불일치",
            content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/users/join")
    public BaseResponse<Long> join(@RequestBody PostJoinReq postJoinReq) {
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
        if(name.isBlank()) return BaseResponseStatus.JOIN_EMPTY_NAME;  // 이름이 비어있는지 확인
        if((int)( Math.log10(password) + 1) != 6) return BaseResponseStatus.JOIN_INVALID_PASSWORD;

        return BaseResponseStatus.SUCCESS;
    }
}
