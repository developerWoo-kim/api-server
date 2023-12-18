package gw.apiserver.common.security.api.service;

import gw.apiserver.common.security.core.JwtTokenProvider;
import gw.apiserver.common.security.core.response.dto.AccessTokenDto;
import gw.apiserver.common.security.exception.custom.RefreshTokenNotFound;
import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import gw.apiserver.oms.auth.domain.AuthGroupUser;
import gw.apiserver.oms.auth.repository.AuthGroupRepository;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import gw.apiserver.oms.user.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JwtApiService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AuthGroupRepository authGroupRepository;

    /**
     * Access Token 갱신
     * @param req HttpServletRequest
     * @param rep HttpServletResponse
     * @return AccessTokenDto
     */
    public AccessTokenDto getAccessToken(HttpServletRequest req, HttpServletResponse rep) {
        String refreshToken = Optional
                .ofNullable(jwtTokenProvider.resolveRefreshToken(req))
                .orElseThrow(() -> new GlobalApiException(CommonError.CMM_AUTH_REFRESH_TOKEN_NOT_FOUND));

        AccessTokenDto accessTokenDto = null;
        if(StringUtils.hasText(refreshToken) && jwtTokenProvider.validateToken(refreshToken, req, rep)) {
            Claims claims = jwtTokenProvider.parseClaims(refreshToken);
            String memberId = claims.getSubject();
            User user = userRepository.findByUserId(memberId)
                    .orElseThrow(() -> new GlobalApiException(CommonError.CMM_SYSTEM_ERROR));

            Optional<List<AuthGroupUser>> findAuthGroupUser =
                    authGroupRepository.findAuthGroupUserWithFetch(user.getUserSn());

            String roleStr = "";
            if(findAuthGroupUser.isPresent()) {
                List<AuthGroupUser> authGroupUser = findAuthGroupUser.get();
                for(int i = 0; i < authGroupUser.size(); i++) {
                    roleStr = authGroupUser.get(i).getAuthGroup().getRoleStr();
                    if(i < authGroupUser.size()-1)
                    roleStr += ",";
                }
            }


            accessTokenDto = jwtTokenProvider.generateAccessTokenDto(memberId, roleStr);
        }

        return accessTokenDto;
    }

}
