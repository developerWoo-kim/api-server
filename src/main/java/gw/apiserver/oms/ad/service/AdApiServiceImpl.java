package gw.apiserver.oms.ad.service;

import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.repository.AdMngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdApiServiceImpl implements AdApiService {
    private final AdMngRepository adMngRepository;

    @Override
    public Page<AdListDto> searchAdPagingList(SearchCondition condition, Pageable pageable) {
        Page<AdListDto> adPageList = adMngRepository.findAdPageList(condition, pageable);

        for (AdListDto adList : adPageList.getContent()) {
            List<AdListDto.AplctCurrentStatusDto> aplctCurrentStatus = adMngRepository.findAplctCurrentStatus(adList.getAdSn());
            adList.setAplctCurrentStatus(aplctCurrentStatus);

            int matchingCnt = 0;
            int psbltyCnt = 0;
            for (AdListDto.AplctCurrentStatusDto currentStatus : aplctCurrentStatus) {
                matchingCnt += currentStatus.getMatchingCnt();
                psbltyCnt += currentStatus.getAplctPsbltyCnt();
            }

            adList.addMatchingCnt(matchingCnt);
            adList.addPasbltyCnt(psbltyCnt);
        }

        return adPageList;
    }

}
