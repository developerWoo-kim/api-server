package gw.apiserver.common.security.core.response;

import lombok.Getter;

@Getter
public enum JwtExceptionCode {
    TOKEN_MALFORMED("jwt-001", "손상된 토큰입니다."),
    TOKEN_EXPIRED("jwt-002", "만료된 토큰입니다."),
    TOKEN_UNSUPPORTED("jwt-003", "지원하지 않는 토큰입니다."),
    TOKEN_ILLEGAL_ARGUMENT("jwt-004", "시그니처 검증에 실패했습니다.");

    public final String errorCode;
    public final String message;

    JwtExceptionCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
