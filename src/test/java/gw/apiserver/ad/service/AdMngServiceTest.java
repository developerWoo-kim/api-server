package gw.apiserver.ad.service;

import gw.apiserver.oms.ad.controller.queryDto.AdApplyDto;
import gw.apiserver.oms.ad.domain.AdConditi;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdPrefer;
import gw.apiserver.oms.ad.repository.AdMngRepository;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
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
}
