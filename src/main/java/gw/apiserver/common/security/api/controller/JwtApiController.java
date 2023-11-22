package gw.apiserver.common.security.api.controller;


import gw.apiserver.common.security.api.service.JwtApiService;
import gw.apiserver.common.security.core.response.dto.AccessTokenDto;
import gw.apiserver.common.utils.reponse.error.AbstractErrorResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "token", description = "토큰 발급 API")
@RestController
@RequiredArgsConstructor
public class JwtApiController {
    private final JwtApiService jwtApiService;

    @Operation(
            summary = "Access Token 갱신",
            description = "Refresh Token을  이용한 Access Token 갱신",
            responses = {

                    @ApiResponse(responseCode = "200", description = "Access Token 갱신 성공", content = @Content(schema = @Schema(implementation = AccessTokenDto.class))),
                    @ApiResponse(responseCode = "400", description = "Refresh Token 누락"),
                    @ApiResponse(responseCode = "403", description = "Refresh Token 만료 or Refresh Token 인증 실패", content = @Content(schema = @Schema(implementation = AbstractErrorResponse.class)))
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Refresh",
                    value = "발급 받은 리프레시 토큰",
                    required = true,
                    dataType = "string",
                    paramType = "header")
    })
    @PostMapping("/auth/token")
    public AccessTokenDto getAccessToken(HttpServletRequest req, HttpServletResponse rep) {
        return jwtApiService.getAccessToken(req, rep);
    }

}
