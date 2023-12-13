package gw.apiserver.common.security.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.meta.CommonErrorResponse;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static gw.apiserver.common.utils.reponse.code.CommonError.*;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
            throws IOException {
        ObjectMapper om = new ObjectMapper();

        CommonErrorResponse commonErrorResponse = CommonErrorResponse.builder()
                .code(CMM_AUTH_ROLE_NOT_FOUND.getCode())
                .message(CMM_AUTH_ROLE_NOT_FOUND.getMessage())
                .path(request.getRequestURI())
                .build();

        response.setCharacterEncoding("utf-8");
        response.setStatus(CMM_AUTH_ROLE_NOT_FOUND.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .write(om.writeValueAsString(commonErrorResponse));
    }
}
