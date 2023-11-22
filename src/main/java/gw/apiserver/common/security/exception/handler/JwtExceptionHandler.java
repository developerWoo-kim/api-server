package gw.apiserver.common.security.exception.handler;

import gw.apiserver.common.security.exception.JwtTokenExceptionTypes;
import gw.apiserver.common.security.exception.custom.RefreshTokenNotFound;
import gw.apiserver.common.utils.reponse.error.CommonErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

import static gw.apiserver.common.utils.reponse.code.CommonErrorCode.CMM_AUTH_REFRESH_TOKEN_NOT_FOUND;

@RestControllerAdvice(basePackages = {"gw.apiserver.common.security"})
@Order(Ordered.HIGHEST_PRECEDENCE)      // RestControllerAdvice 우선 순위 조정 : 최우선 순위
public class JwtExceptionHandler {

    @ExceptionHandler(RefreshTokenNotFound.class)
    public ResponseEntity<CommonErrorResponse> handlerRefreshTokenNotFoundException(HttpServletRequest req, RefreshTokenNotFound exception) {
        CommonErrorResponse errorResponse = CommonErrorResponse
                .commonError(HttpStatus.BAD_REQUEST.toString(), req.getRequestURI(), CMM_AUTH_REFRESH_TOKEN_NOT_FOUND.getCode(), CMM_AUTH_REFRESH_TOKEN_NOT_FOUND.getMessage());
        //응답 바디에  errorResponse를 담아 리턴한다.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CommonErrorResponse> handlerJwtException(HttpServletRequest req, JwtException exception) {
        JwtTokenExceptionTypes type = JwtTokenExceptionTypes.findOf(exception.getClass().getSimpleName());

        CommonErrorResponse errorResponse = CommonErrorResponse
                .commonError(HttpStatus.UNAUTHORIZED.toString(), req.getRequestURI(), type.getCode(), type.getMessage());
        //응답 바디에  errorResponse를 담아 리턴한다.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
