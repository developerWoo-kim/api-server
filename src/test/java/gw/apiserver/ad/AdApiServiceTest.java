package gw.apiserver.ad;

import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.repository.AdMngRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdApiServiceTest {
    @Autowired
    AdMngRepository adMngRepository;


    @Test
    @DisplayName("광고 페이징 리스트 조회 테스트")
    public void searchAdPagingList() {
        SearchCondition condition = new SearchCondition();
        Pageable pageRequest = PageRequest.of(0, 20);
        Page<AdListDto> adPageList = adMngRepository.findAdPageList(condition, pageRequest);

        for (AdListDto adList : adPageList.getContent()) {
            List<AdListDto.AplctCurrentStatusDto> aplctCurrentStatus = adMngRepository.findAplctCurrentStatus(adList.getId());
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

        System.out.println(adPageList);
    }
}
