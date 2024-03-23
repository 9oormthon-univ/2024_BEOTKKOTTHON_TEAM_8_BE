package com.example.worrybox.utils.config;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> BaseException(MethodArgumentNotValidException e) {
        // 에러 메시지 추출
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(errorMessage);

        // 적절한 BaseResponseStatus 사용 (여기서는 INVALID_PARAMETERS를 예로 들었습니다)
        return new BaseResponse<>(BaseResponseStatus.INVALID_PARAMETERS, errorMessage);
    }

}
