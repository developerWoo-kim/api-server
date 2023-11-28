package gw.apiserver.oms.ad.repository;

import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.domain.dto.AdMngDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdMngCustomRepository {
    Page<AdListDto> findAdPageList(SearchCondition searchCondition, Pageable pageable);

    List<AdListDto.AplctCurrentStatusDto> findAplctCurrentStatus(String adSn);
}
