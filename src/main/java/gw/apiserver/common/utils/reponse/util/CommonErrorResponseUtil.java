package gw.apiserver.common.utils.reponse.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import gw.apiserver.common.utils.reponse.meta.CommonErrorResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

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
