package gw.apiserver.common.utils.reponse.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import gw.apiserver.common.security.core.response.JwtExceptionCode;
import gw.apiserver.common.utils.reponse.error.CommonErrorResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Getter @Setter
public class CommonErrorResponseUtil {

    public static void sendJsonErrorResponse(HttpServletResponse rep, HttpStatus status, CommonErrorResponse errorResponse) {
        ObjectMapper om = new ObjectMapper();

        rep.setCharacterEncoding("utf-8");
        rep.setStatus(status.value());
        rep.setContentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            rep.getOutputStream()
                    .write(om.writeValueAsString(errorResponse).getBytes("utf-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
