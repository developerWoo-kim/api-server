package gw.apiserver.oms.common.cmmcode.controller;

import gw.apiserver.oms.common.cmmcode.controller.dto.CommonCodeDetailDto;
import gw.apiserver.oms.common.cmmcode.domain.ComtCcmnDetailCode;
import gw.apiserver.oms.common.cmmcode.repository.ComtCcmnDetailCodeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = {"공통 코드 API"})
@RestController
@RequiredArgsConstructor
public class CmmCodeApiController {
    private final ComtCcmnDetailCodeRepository codeRepository;

    /**
     * 공통 코드 조회 API
     * @param codeId String
     * @return ResponseEntity<List<CommonCodeDetailDto>>
     */
    @Operation(
            summary = "공통 코드 조회",
            description = "공통 코드 조회",
            responses = {

                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "401", description = "Access Token 누락"),
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
    @GetMapping("/api/v1/code/detail/{codeId}")
    public ResponseEntity<List<CommonCodeDetailDto>> findCmmCodeDetail(@PathVariable("codeId") String codeId) {
        List<CommonCodeDetailDto> cmmCodeDetailList = codeRepository.findCmmCodeDetail(codeId);
        return ResponseEntity.status(HttpStatus.OK).body(cmmCodeDetailList);
    }
}
