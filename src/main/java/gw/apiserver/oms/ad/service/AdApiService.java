package gw.apiserver.oms.ad.service;

import gw.apiserver.common.paging.PagingForm;
import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.common.utils.pagination.Pagination;
import gw.apiserver.oms.ad.controller.queryDto.AdApplyDto;
import gw.apiserver.oms.ad.controller.queryDto.AdMatchListDto;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 광고 Api 서비스
 */
public interface AdApiService {

    /**
     * 광고 페이징 리스트 조회
     * @param condition SearchCondition
     * @param pageable Pageable
     * @return Page<AdListDto>
     */
    Page<AdListDto> searchAdPagingList(SearchCondition condition, Pageable pageable);

    Pagination<AdListDto> adPagingList(PagingForm form);

    /**
     * 광고 응모
     * @param adSn String 광고 일련번호
     * @param userId String 회원 아이디
     */
    void applyAd(String adSn, String userId);

    /**
     * 고객이 응모한 광고 내역 조회 (매칭된 광고는 조회 X)
     * @param userId String
     * @return List<AdApplyDto>
     */
    List<AdApplyDto> findApplyAdList(String userId);

    Pagination<AdMatchListDto> findMatchAdList(PagingForm form, String userSn);
}
