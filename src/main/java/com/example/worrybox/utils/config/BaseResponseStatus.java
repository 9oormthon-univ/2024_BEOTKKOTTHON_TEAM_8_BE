package com.example.worrybox.utils.config;

import lombok.Getter;

/**
 * Error code
 */
@Getter
public enum BaseResponseStatus {
    /** 기본 Response **/
    SUCCESS(true, 200, "요청에 성공하였습니다."),
    INVALID_REQUEST(false, 400, "잘못된 요청입니다"),
    INVALID_HEADER(false, 401, "헤더 없음 or 토큰 불일치"),

    /** User **/
    BASE_INVALID_USER(false, 4000, "존재하지 않는 유저입니다."),
    JOIN_INVALID_NAME(false, 4001, "중복된 이름입니다."),
    JOIN_INVALID_PASSWORD(false, 4002, "비밀번호는 6자리여야 합니다."),
    JOIN_EXIST_USER(false, 4003, "이미 회원가입된 유저입니다."),


    /** Letter **/


    /** Memo **/
    INVALID_USER(false, 6000,"유저로 메모를 찾을 수 없습니다."),
    INVALID_MEMO(false, 6001,"메모를 찾을 수 없습니다."),
    RESPONSE_FAILURE(false, 6002, "AI 생성에 실패했습니다."),

    /** 기타 **/
    WRONG_STATUS_CODE(false, 5000, "존재하지 않은 상태코드입니다."),
    INVALID_PARAMETERS(false, 400, "입력값이 잘못되었습니다.");

    // BaseResponseStatus Mapping
    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public static BaseResponseStatus getBaseStatusByCode(int code) throws BaseException {
        for (BaseResponseStatus baseStatus: BaseResponseStatus.values()) {
            if (baseStatus.code == code) {
                return baseStatus;
            }
        }
        throw new BaseException(BaseResponseStatus.WRONG_STATUS_CODE);
    }
}
