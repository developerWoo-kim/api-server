package gw.apiserver.common.utils.reponse.exception.handler;

import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.meta.CommonErrorResponse;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CommonError error = CommonError.CMM_INVALID_PARAMETER;
        return handleExceptionInternal(e, request, error);
    }

    private ResponseEntity<Object> handleExceptionInternal(HttpServletRequest req, CommonError error) {
        return ResponseEntity.status(error.getStatus())
                .body(makeErrorResponse(req, error));
    }

    private ResponseEntity<Object> handleExceptionInternal(HttpServletRequest req, CommonError error, String message) {
        return ResponseEntity.status(error.getStatus())
                .body(makeErrorResponse(req, error, message));
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, WebRequest request, CommonError error) {
        return ResponseEntity.status(error.getStatus())
                .body(makeErrorResponse(e, request, error));
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

    private CommonErrorResponse makeErrorResponse(BindException e, WebRequest request, CommonError error) {
        List<CommonErrorResponse.ValidationError> errorList = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(CommonErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return CommonErrorResponse.builder()
                .code(error.getCode())
                .message(error.getMessage())
                .path(((ServletWebRequest) request).getRequest().getRequestURI())
                .errors(errorList)
                .build();
    }

}
