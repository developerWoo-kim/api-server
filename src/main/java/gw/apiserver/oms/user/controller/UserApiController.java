package gw.apiserver.oms.user.controller;

import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"회원 API"})
@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @Operation(
            summary = "회원 아이디 중복 체크",
            description = "회원 아이디 중복 체크",
            responses = {

                    @ApiResponse(responseCode = "200", description = "사용 가능 아이디"),
                    @ApiResponse(responseCode = "400", description = "중복된 아이디"),
            }
    )
//    @ApiImplicitParams({
//            @ApiImplicitParam(
//                    name = "authorization",
//                    value = "Access Token",
//                    required = true,
//                    dataType = "string",
//                    paramType = "header"
//            )
//    })
    @GetMapping("/api/v1/user/{id}/id-duple-check")
    public ResponseEntity<CommonResponse> idDuplicationCheck(@PathVariable("id") String id) {
        return userService.idDuplicationCheck(id);
    }
}
