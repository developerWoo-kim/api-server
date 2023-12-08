package gw.apiserver.common.security.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import gw.apiserver.common.security.exception.AuthenticationExceptionTypes;
import gw.apiserver.common.utils.reponse.error.CommonErrorResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security - 로그인 실패 핸들러
 *
 * @author kimgunwoo
 * @since 2023.10.11
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        AuthenticationExceptionTypes exceptionTypes = AuthenticationExceptionTypes.findOf(exception.getClass().getSimpleName());

        ObjectMapper om = new ObjectMapper();
        CommonErrorResponse commonErrorResponse = CommonErrorResponse.commonError(
                exceptionTypes.getCode(),
                exceptionTypes.getMessage(),
                request.getRequestURI()
        );

        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .write(om.writeValueAsString(commonErrorResponse));
    }
}
