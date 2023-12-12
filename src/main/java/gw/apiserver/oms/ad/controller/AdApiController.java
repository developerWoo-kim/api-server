package gw.apiserver.oms.ad.controller;

import gw.apiserver.common.paging.PagingDto;
import gw.apiserver.common.paging.PagingForm;
import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.common.security.core.JwtTokenProvider;
import gw.apiserver.common.utils.reponse.meta.CommonErrorResponse;
import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.service.AdApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"광고 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
public class AdApiController {
    private final AdApiService adApiService;
    private final JwtTokenProvider tokenProvider;

    @Operation(
            summary = "광고 페이징 리스트 조회",
            description = "광고 페이징 리스트 조회",
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
    @GetMapping("/api/v1/ad/page-list")
    public PagingDto<AdListDto> findAdList(SearchCondition condition, PagingForm pagingForm) {
        Page<AdListDto> page = adApiService.searchAdPagingList(condition, pagingForm.createPageable());
        PagingDto<AdListDto> paging = new PagingDto<>(page.getContent(), page.getTotalElements(), page.getNumber(), page.getSize());
        return paging;
    }

    @Operation(
            summary = "광고 응모",
            description = "광고를 응모합니다.",
            responses = {

                    @ApiResponse(responseCode = "200", description = "응모 완료"),
                    @ApiResponse(responseCode = "401", description = "Access Token 누락", content = @Content(
                            schema = @Schema(implementation = CommonErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Refresh Token 만료 or Refresh Token 인증 실패", content = @Content(
                            schema = @Schema(implementation = CommonErrorResponse.class))),
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
    @PostMapping("/api/v1/ad/apply/{adSn}")
    public ResponseEntity<CommonResponse> apply(@PathVariable("adSn") String adSn, HttpServletRequest req) {
        String userId = tokenProvider.getUserId(req);
        adApiService.applyAd(adSn, userId);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.createResponse(HttpStatus.OK.toString(), "응모 되었습니다."));
    }



    @PostMapping("/api/fileTest")
    public ResponseEntity<CommonResponse> fileTest(@RequestParam("image") MultipartFile file) {
        System.out.println(file.getName());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.createResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "서버 오류"));
    }
}
