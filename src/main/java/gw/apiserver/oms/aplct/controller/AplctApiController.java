package gw.apiserver.oms.aplct.controller;


import gw.apiserver.common.utils.reponse.meta.CommonErrorResponse;
import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.aplct.controller.form.AdProofForm;
import gw.apiserver.oms.aplct.service.AplctprgrsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@Api(tags = {"광고 진행 회차 API"})
@RestController
@RequiredArgsConstructor
public class AplctApiController {
    private final AplctprgrsService aplctprgrsService;

    @Operation(
            summary = "진행한 광고 증빙",
            description = "진행한 광고 증빙",
            responses = {

                    @ApiResponse(responseCode = "200", description = "성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 누락",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommonErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Refresh Token 만료 or Refresh Token 인증 실패")
            }
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "authorization",
                    value = "Access Token",
                    required = true,
                    dataType = "string",
                    paramType = "header"
            )
    })
    @PutMapping("/api/v1/aplct/proof")
    public ResponseEntity<CommonResponse> adProof(@Valid AdProofForm form) {
        aplctprgrsService.doAdProof(form);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.createResponse(HttpStatus.OK.toString(), "증빙 등록이 완료되었습니다."));
    }
}
