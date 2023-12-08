package gw.apiserver.common.utils.reponse.exception.handler;

import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.error.CommonErrorResponse;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(GlobalApiException.class)
    public ResponseEntity<Object> handlerGlobalApiException(HttpServletRequest req, GlobalApiException e) {
        return handleExceptionInternal(req, e.getErrorCode());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(HttpServletRequest req, IllegalArgumentException e) {
        log.warn("handleIllegalArgument", e);
        CommonError errorCode = CommonError.CMM_INVALID_PARAMETER;
        return handleExceptionInternal(req, errorCode, e.getMessage());
    }

    private ResponseEntity<Object> handleExceptionInternal(HttpServletRequest req, CommonError error) {
        return ResponseEntity.status(error.getStatus())
                .body(makeErrorResponse(req, error));
    }

    private ResponseEntity<Object> handleExceptionInternal(HttpServletRequest req, CommonError error, String message) {
        return ResponseEntity.status(error.getStatus())
                .body(makeErrorResponse(req, error, message));
    }

    private CommonErrorResponse makeErrorResponse(HttpServletRequest req, CommonError error) {
        return CommonErrorResponse.builder()
                .code(error.getCode())
                .message(error.getMessage())
                .path(req.getRequestURI())
                .build();
    }

    private CommonErrorResponse makeErrorResponse(HttpServletRequest req, CommonError error, String message) {
        return CommonErrorResponse.builder()
                .code(error.getCode())
                .message(message)
                .path(req.getRequestURI())
                .build();
    }

}
