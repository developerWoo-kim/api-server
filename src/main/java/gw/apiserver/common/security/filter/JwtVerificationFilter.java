package gw.apiserver.common.security.filter;

import gw.apiserver.common.security.core.userdetails.CustomUserDetails;
import gw.apiserver.common.security.core.JwtTokenProvider;
import gw.apiserver.common.security.core.response.JwtExceptionCode;
import gw.apiserver.common.security.core.response.JwtResponseUtil;
import gw.apiserver.common.security.exception.JwtTokenExceptionTypes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * JWT 기반 권한, 인증 필터
 *
 * -> 시큐리티 filter chain 중 OncePerRequestFilter 와 BasicAuthenticationFilter가 있음.
 * -> 상위 클래스인 BasicAuthenticationFilter가 적절해 보여 사용
 * -> 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있음.
 * -> 만약 권한이 인증이 필요한 주소가 아니라면 이 필터를 안탐
 *
 * @author kimgunwoo
 * @since 2023.11.14
 * @version 1.0v
 */
@Slf4j
public class JwtVerificationFilter extends BasicAuthenticationFilter {
    private static final List<String> EXCLUDE_URL =
            List.of("/", "/members/signup", "/auth/login", "/auth/reissue");
    private final JwtTokenProvider jwtTokenProvider;

    public JwtVerificationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.resolveAccessToken(request);

        try {
            if(StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken, request, response)) {
                // JWT 토큰을 복호화하여 토큰 정보를 반환
                Claims claims = jwtTokenProvider.parseClaims(accessToken);
                String authority = claims.get("role").toString();

                CustomUserDetails customUserDetails = CustomUserDetails.of(
                        claims.getSubject(),
                        authority);

                log.info("# AuthMember.getRoles 권한 체크 = {}", customUserDetails.getAuthorities().toString());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        customUserDetails,
                        null,
                        customUserDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException e) {
            JwtTokenExceptionTypes jwtTokenExceptionTypes = JwtTokenExceptionTypes.findOf(e.getClass().getSimpleName());

            JwtResponseUtil.sendErrorResponse(request, response, JwtExceptionCode.TOKEN_MALFORMED);

            System.out.println(jwtTokenExceptionTypes);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * EXCLUDE_URL과 동일한 요청이 들어왔을 경우, 현재 필터를 진행하지 않고 다음 필터 진행
     *
     * @param request current HTTP request
     * @return boolean
     * @throws ServletException HttpServletRequest
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean result = EXCLUDE_URL.stream()
                .anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));

        return result;
    }
}
