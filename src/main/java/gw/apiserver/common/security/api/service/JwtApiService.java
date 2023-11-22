package gw.apiserver.common.security.api.service;

import gw.apiserver.common.security.core.JwtTokenProvider;
import gw.apiserver.common.security.core.response.dto.AccessTokenDto;
import gw.apiserver.common.security.exception.custom.RefreshTokenNotFound;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtApiService {
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Access Token 갱신
     * @param req HttpServletRequest
     * @param rep HttpServletResponse
     * @return AccessTokenDto
     */
    public AccessTokenDto getAccessToken(HttpServletRequest req, HttpServletResponse rep) {
        String refreshToken = Optional
                .ofNullable(jwtTokenProvider.resolveRefreshToken(req))
                .orElseThrow(RefreshTokenNotFound::new);

        AccessTokenDto accessTokenDto = null;
        if(StringUtils.hasText(refreshToken) && jwtTokenProvider.validateToken(refreshToken, req, rep)) {
            Claims claims = jwtTokenProvider.parseClaims(refreshToken);
            String memberId = claims.getSubject();
            accessTokenDto = jwtTokenProvider.generateAccessTokenDto(memberId);
        }

        return accessTokenDto;
    }

}
