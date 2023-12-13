package gw.apiserver.common.security.authentication;

import gw.apiserver.common.security.core.userdetails.CustomUserDetails;
import gw.apiserver.oms.auth.domain.AuthGroupApi;
import gw.apiserver.oms.auth.domain.AuthGroupRole;
import gw.apiserver.oms.common.api.domain.ApiMng;
import gw.apiserver.oms.common.api.service.ApiMngServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 *	Class Name	: AuthorizationChecker.java
 *	Description	: 클라이언트 요청에 대한 인가 여부를 확인
 *                 - ignore uri을 제외한 모든 요청의 인가 여부를 확인
 *
 *	Modification Information
 *	수정일		수정자		수정 내용
 *	-----------	----------	---------------------------
 *	2023.10.24	gwkim		최초 생성
 *  2023.10.31	gwkim		메뉴 권한 체킹 로직 단순화, SecurityAuthorUtil
 *
 *
 *	@author  gwkim
 *	@since  2023.10.24
 *	@version 1.1
 */
@Component
@Transactional
@RequiredArgsConstructor
public class AuthorizationChecker {
    private final ApiMngServiceImpl apiMngService;

    public boolean check(HttpServletRequest request, Authentication authentication) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // TODO 추후 리팩토링 해야함
        // API 권한 체크
        List<ApiMng> apiList = apiMngService.findAllApi();
        for (ApiMng apiMng : apiList) {
            if(apiMng.uriMatcher(url, method)) {
                List<AuthGroupApi> authGroupApiList = apiMng.getAuthGroupApiList();
                // API에 등록된 권한이 없다면 access 허용
                if(!authGroupApiList.isEmpty()) {
                    for (AuthGroupApi authGroupApi : authGroupApiList) {
                        for(AuthGroupRole roleList : authGroupApi.getAuthGroup().getRoleList()) {
                            boolean matches = roleList.getId().getAuthrtRole().matches(userDetails.getRoles());
                            if(matches) {
                                return true;
                            }
                        }
                    }
                } else {
                    return true;
                }
            };

        }

        return false;
    }
    
}
