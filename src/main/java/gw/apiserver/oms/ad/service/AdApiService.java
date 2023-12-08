package gw.apiserver.oms.ad.service;

import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    /**
     * 광고 응모
     * @param adSn String 광고 일련번호
     * @param userId String 회원 아이디
     */
    void applyAd(String adSn, String userId);
}
