package gw.apiserver.ad.service;

import gw.apiserver.common.utils.pagination.Pagination;
import gw.apiserver.oms.ad.controller.queryDto.AdApplyDto;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.domain.AdConditi;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdPrefer;
import gw.apiserver.oms.ad.repository.AdMngRepository;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdMngServiceTest {
    @Autowired
    AdMngRepository adMngRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("고객이 응모한 광고 목록 조회 테스트")
    void findApplyAdList() {
        String userSn = "USR_00000023";

        User user = userRepository.findById(userSn).orElseThrow();
        List<AdMng> applyAdList = adMngRepository.findApplyAdList(userSn);

        if(applyAdList.isEmpty()) {
           throw new RuntimeException("응모 내역이 없습니다.");
        }

        List<AdApplyDto> adApplyDtoList = new ArrayList<>();
        for (AdMng adMng : applyAdList) {
            AdApplyDto adApplyDto = new AdApplyDto(adMng);
            String adSn = adMng.getAdSn();

            // 우대 조건 조회
            List<AdPrefer> adPrefer = adMngRepository.findAdPrefer(adSn);
            if(!adPrefer.isEmpty()) {
                for (AdPrefer prefer : adPrefer) {
                    adApplyDto.addPrefer(prefer);
                }
            }

            // 응모 조건에서 배당금 조회
            List<AdConditi> findAdConditi = adMngRepository.findAdConditi(adSn);
            if(!findAdConditi.isEmpty()) {
                for (AdConditi adConditi : findAdConditi) {
                    adApplyDto.addConditi(adConditi);
                }
            }
            // 응모 조건에서 회원에 해당되는 배당금 조회
            adMngRepository.findAdConditi(adSn).stream()
                    .filter(adConditi -> adConditi.getVhclLoadweightCd().equals(user.getVhclLoadweightCd()))
                    .findFirst()
                    .ifPresent(adConditi -> adApplyDto.addUserAllocAmt(adConditi.getAllocAmt()));


            adApplyDtoList.add(adApplyDto);
        }

    }

    @Test
    @DisplayName("광고 페이징 목록 NO OFFSET 테스트")
    void adNoOffsetPagingList() {
        List<AdListDto> adList = adMngRepository.findAdList(5, "AD_0000228");

        Assertions.assertThat(adList.size()).isEqualTo(5);
        Assertions.assertThat(adList.get(0).getId()).isEqualTo("AD_0000227");
//        Assertions.assertThat(adList.get(1).getAdSn()).isEqualTo("AD_0000231");
//        Assertions.assertThat(adList.get(2).getAdSn()).isEqualTo("AD_0000230");
//        Assertions.assertThat(adList.get(3).getAdSn()).isEqualTo("AD_0000229");
//        Assertions.assertThat(adList.get(4).getAdSn()).isEqualTo("AD_0000228");
    }

    @Test
    @DisplayName("광고 페이징 목록 NO OFFSET 테스트 - 카운트 테스트")
    void adNoOffsetPagingList_countTest() {
        List<AdListDto> adList = adMngRepository.findAdList(5, "AD_0000009");

        Assertions.assertThat(adList.size()).isEqualTo(2);
        Assertions.assertThat(adList.get(0).getId()).isEqualTo("AD_0000008");
        Assertions.assertThat(adList.get(1).getId()).isEqualTo("AD_0000007");
//        Assertions.assertThat(adList.get(1).getAdSn()).isEqualTo("AD_0000231");
//        Assertions.assertThat(adList.get(2).getAdSn()).isEqualTo("AD_0000230");
//        Assertions.assertThat(adList.get(3).getAdSn()).isEqualTo("AD_0000229");
//        Assertions.assertThat(adList.get(4).getAdSn()).isEqualTo("AD_0000228");
    }

    @Test
    @DisplayName("광고 페이징 목록 NO OFFSET - hasNextData 테스트")
    void adNoOffsetPagingList_hasNextData() {
        Optional<AdMng> AdMng = adMngRepository.hasNextData("AD_0000009");

        Assertions.assertThat(AdMng).isNotEmpty();
//        Assertions.assertThat(adList.get(1).getAdSn()).isEqualTo("AD_0000231");
//        Assertions.assertThat(adList.get(2).getAdSn()).isEqualTo("AD_0000230");
//        Assertions.assertThat(adList.get(3).getAdSn()).isEqualTo("AD_0000229");
//        Assertions.assertThat(adList.get(4).getAdSn()).isEqualTo("AD_0000228");
    }

    @Test
    @DisplayName("광고 페이징 목록 NO OFFSET - hasNextData 테스트")
    void adNoOffsetPagingList_isNotExitNextData() {
        Optional<AdMng> AdMng = adMngRepository.hasNextData("AD_0000007");
        Assertions.assertThat(AdMng).isEmpty();
    }

    @Test
    @DisplayName("광고 페이징 목록 생성 테스트")
    void adNoOffsetPagingList_createTest() {
        List<AdListDto> adList = adMngRepository.findAdList(5, "AD_0000007");

        boolean hasNextData = !adList.isEmpty() && adMngRepository.hasNextData(adList.get(adList.size() - 1).getId()).isPresent();
        Pagination<AdListDto> pagination = Pagination.createPagination(hasNextData, adList);

        Assertions.assertThat(pagination.getCount()).isEqualTo(0);
        Assertions.assertThat(pagination.isHasNextData()).isEqualTo(false);
//        Assertions.assertThat(adList.get(1).getAdSn()).isEqualTo("AD_0000231");
//        Assertions.assertThat(adList.get(2).getAdSn()).isEqualTo("AD_0000230");
//        Assertions.assertThat(adList.get(3).getAdSn()).isEqualTo("AD_0000229");
//        Assertions.assertThat(adList.get(4).getAdSn()).isEqualTo("AD_0000228");
    }


}
