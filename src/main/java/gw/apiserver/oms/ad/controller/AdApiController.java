package gw.apiserver.oms.ad.controller;

import gw.apiserver.common.paging.PagingDto;
import gw.apiserver.common.paging.PagingForm;
import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.common.security.core.JwtTokenProvider;
import gw.apiserver.common.utils.pagination.Pagination;
import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import gw.apiserver.common.utils.reponse.meta.CommonErrorResponse;
import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.ad.controller.queryDto.AdApplyDto;
import gw.apiserver.oms.ad.controller.queryDto.AdMatchListDto;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.service.AdApiService;
import gw.apiserver.oms.user.controller.form.UserUpdateForm;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = {"광고 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
public class AdApiController {
    private final AdApiService adApiService;
    private final UserRepository userRepository;
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
            summary = "광고 no-offest paging 조회",
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
    @GetMapping("/api/v1/ad/no-offset-page")
    public Pagination<AdListDto> findAdNoOffsetPageList(PagingForm pagingForm) {
        return adApiService.adPagingList(pagingForm);
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
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponse.createResponse(HttpStatus.OK.toString(), "응모 되었습니다."));
    }

    @Operation(
            summary = "고객이 응모한 광고 목록 조회",
            description = "고객이 응모한 광고 목록 조회",
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
    @GetMapping("/api/v1/ad/apply-list")
    public ResponseEntity<List<AdApplyDto>> findApplyAdList(HttpServletRequest req) {
        String userId = tokenProvider.getUserId(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(adApiService.findApplyAdList(userId));
    }

    @Operation(
            summary = "고객의 광고 매칭 목록 조회",
            description = "고객의 광고 매칭 목록 조회",
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
    @GetMapping("/api/v1/ad/match-list")
    public Pagination<AdMatchListDto> findAdMatchList(HttpServletRequest req, PagingForm pagingForm) {
        String userId = tokenProvider.getUserId(req);
        User user = userRepository.findByUserId(userId).orElseThrow();
        Pagination<AdMatchListDto> matchAdList = adApiService.findMatchAdList(pagingForm, user.getUserSn());

        if(matchAdList.getData().isEmpty()) {
            throw new GlobalApiException(CommonError.APLCT_AD_NOT_FOUND);
        }
        return adApiService.findMatchAdList(pagingForm, user.getUserSn());
    }


    @PostMapping("/api/fileTest")
    public ResponseEntity<CommonResponse> fileTest(UserUpdateForm form) {
        System.out.println(form.getUserSn());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.createResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "서버 오류"));
    }
}
