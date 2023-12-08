package gw.apiserver.common.security.core.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import gw.apiserver.common.security.exception.JwtTokenExceptionTypes;
import gw.apiserver.common.utils.reponse.error.CommonErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtResponseUtil {

    public static void sendErrorResponse(HttpServletRequest request, HttpServletResponse response, JwtExceptionCode jwtExceptionCode) throws IOException {
        ObjectMapper om = new ObjectMapper();

        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        CommonErrorResponse commonErrorResponse = CommonErrorResponse.commonError(
                jwtExceptionCode.getErrorCode(),
                jwtExceptionCode.getMessage(),
                request.getRequestURI()
        );

        response.getWriter()
                .write(om.writeValueAsString(ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .body(commonErrorResponse))
                );
    }

//    public String getErrorMessageByJwtException(JwtException jwtException) {
//        JwtTokenExceptionTypes jwtTokenExceptionTypes = JwtTokenExceptionTypes.findOf(jwtException.getClass().getSimpleName());
//
//        switch (jwtTokenExceptionTypes) {
//            case MalformedJwtException:
//            case ExpiredJwtException:
//            case UnsupportedJwtException:
//            case IllegalArgumentException:
//            case SignatureException:
//        }
//    }
}
