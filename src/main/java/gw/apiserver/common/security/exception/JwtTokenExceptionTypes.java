package gw.apiserver.common.security.exception;

import gw.apiserver.common.security.core.response.JwtExceptionCode;
import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gw.apiserver.common.security.core.response.JwtExceptionCode.*;

@Getter
public enum JwtTokenExceptionTypes {
    MalformedJwtException(TOKEN_MALFORMED.getErrorCode(), TOKEN_MALFORMED.getMessage()),
    ExpiredJwtException(TOKEN_EXPIRED.getErrorCode(), TOKEN_EXPIRED.getMessage()),
    UnsupportedJwtException(TOKEN_UNSUPPORTED.getErrorCode(), TOKEN_UNSUPPORTED.getMessage()),
    IllegalArgumentException(TOKEN_ILLEGAL_ARGUMENT.getErrorCode(), TOKEN_ILLEGAL_ARGUMENT.getMessage()),
    SignatureException(TOKEN_ILLEGAL_ARGUMENT.getErrorCode(), TOKEN_ILLEGAL_ARGUMENT.getMessage());

    private final String errorCode;
    private final String message;


    JwtTokenExceptionTypes(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    private static final Map<String, JwtTokenExceptionTypes> descriptions = Collections
            .unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(JwtTokenExceptionTypes::name, Function.identity())));

    public static JwtTokenExceptionTypes findOf(String findValue) {
        return Optional.ofNullable(descriptions.get(findValue)).orElse(SignatureException);
    }
}
