package gw.apiserver.common.security.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import gw.apiserver.common.security.exception.AuthenticationExceptionTypes;
import gw.apiserver.common.utils.reponse.meta.CommonErrorResponse;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        AuthenticationExceptionTypes exceptionTypes = AuthenticationExceptionTypes.findOf(authException.getClass().getSimpleName());

        ObjectMapper om = new ObjectMapper();

        CommonErrorResponse commonErrorResponse = CommonErrorResponse.builder()
                .code(exceptionTypes.getCode())
                .message(exceptionTypes.getMessage())
                .path(request.getRequestURI())
                .build();

        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .write(om.writeValueAsString(commonErrorResponse));
    }
}
