package gw.apiserver.oms.ad.controller;

import gw.apiserver.common.paging.PagingDto;
import gw.apiserver.common.paging.PagingForm;
import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.service.AdApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.http.HttpResponse;

@Api(tags = {"광고 API"})
@RestController
@RequiredArgsConstructor
public class AdApiController {
    private final AdApiService adApiService;

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

    @PostMapping("/api/fileTest")
    public ResponseEntity<CommonResponse> fileTest(@RequestParam("image") MultipartFile file) {
        System.out.println(file.getName());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CommonResponse.createResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "서버 오류"));
    }
}
