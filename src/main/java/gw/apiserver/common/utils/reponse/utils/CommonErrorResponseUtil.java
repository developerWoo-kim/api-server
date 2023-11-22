package gw.apiserver.common.utils.reponse.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import gw.apiserver.common.security.core.response.JwtExceptionCode;
import gw.apiserver.common.utils.reponse.error.CommonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommonErrorResponseUtil {
    public static void sendJsonErrorResponse(HttpServletRequest req, HttpServletResponse rep, HttpStatus status, String code, String message) {
        ObjectMapper om = new ObjectMapper();

        rep.setCharacterEncoding("utf-8");
        rep.setStatus(status.value());
        rep.setContentType(MediaType.APPLICATION_JSON_VALUE);

        CommonErrorResponse commonErrorResponse = CommonErrorResponse.commonError(
                status.toString(),
                req.getRequestURI(),
                code,
                message
        );

        try {
            rep.getWriter()
                    .write(om.writeValueAsString(commonErrorResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
