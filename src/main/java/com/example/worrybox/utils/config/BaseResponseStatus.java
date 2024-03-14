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
    JOIN_EMPTY_NAME(false, 4000, "보관함 이름이 필요합니다."),
    JOIN_EMPTY_PASSWORD(false, 4001, "보관함 비밀번호가 필요합니다."),
    JOIN_INVALID_NAME(false, 4002, "중복된 이름입니다."),
    JOIN_INVALID_PASSWORD(false, 4003, "비밀번호는 6자리여야 합니다."),

    /** Letter **/

    WRONG_STATUS_CODE(false, 5000, "존재하지 않은 상태코드입니다"),

    /** Memo **/
    INVALID_USER(false, 6000,"유저를 찾을 수 없습니다.");

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
