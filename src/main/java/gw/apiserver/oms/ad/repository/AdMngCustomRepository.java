package gw.apiserver.oms.ad.repository;

import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.domain.AdConditi;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdPrefer;
import gw.apiserver.oms.ad.domain.dto.AdMngDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdMngCustomRepository {
    Page<AdListDto> findAdPageList(SearchCondition searchCondition, Pageable pageable);

    List<AdListDto.AplctCurrentStatusDto> findAplctCurrentStatus(String adSn);

    /**
     * 광고 응모 조건 조회
     * @param adSn String
     * @return List<AdConditi>
     */
    List<AdConditi> findAdConditi(String adSn);


    /**
     * 광고 우대 조건 조회
     * @param adSn String
     * @return List<AdPrefer>
     */
    List<AdPrefer> findAdPrefer(String adSn);

    /**
     * 고객이 응모한 광고 목록 조회 (매칭된 광고는 X)
     * @param userSn String
     * @return List<AdMng>
     */
    List<AdMng> findApplyAdList(String userSn);

}
