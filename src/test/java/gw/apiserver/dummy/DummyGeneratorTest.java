package gw.apiserver.dummy;

import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdSttsCd;
import gw.apiserver.oms.ad.repository.AdMngRepository;
import gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd;
import gw.apiserver.oms.aplct.domain.AplctUserMng;
import gw.apiserver.oms.aplct.domain.Aplctprgrs;
import gw.apiserver.oms.aplct.repository.AplctUserMngRepository;
import gw.apiserver.oms.aplct.repository.AplctprgrsRepository;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DummyGeneratorTest {
    @Autowired DummyGenerator dummyGenerator;
    @Autowired UserRepository userRepository;
    @Autowired AdMngRepository adMngRepository;
    @Autowired AplctUserMngRepository aplctUserMngRepository;
    @Autowired AplctprgrsRepository aplctprgrsRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("회원 생성 테스트")
    void createUserTest() {
        User driverUser = dummyGenerator.createDriverUser();
        Assertions.assertThat(userRepository.findById(driverUser.getUserSn())).isNotEmpty();
    }

    @Test
    @DisplayName("광고주 생성 테스트")
    void createAdUserTest() {
        User adUser = dummyGenerator.createAdUser();

        Assertions.assertThat(userRepository.findById(adUser.getUserSn())).isNotEmpty();
    }

    @Test
    @DisplayName("광고 생성 테스트")
    void createAdTest() {
        User adUser = dummyGenerator.createAdUser();

        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 29);
        LocalDate aplctStart = LocalDate.of(2023, 11, 15);
        LocalDate aplctEnd = LocalDate.of(2024, 11, 20);
        AdMng ad = dummyGenerator.createAd(adUser, AdSttsCd.MNG003002, startDate, endDate, aplctStart, aplctEnd);

        Assertions.assertThat(adMngRepository.findById(ad.getAdSn())).isNotEmpty();
    }

    @Test
    @DisplayName("응모자 생성 테스트")
    void createAplctUserTest() {
        User adUser = dummyGenerator.createAdUser();
        Assertions.assertThat(userRepository.findById(adUser.getUserSn())).isNotEmpty();

        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 29);
        LocalDate aplctStart = LocalDate.of(2023, 11, 15);
        LocalDate aplctEnd = LocalDate.of(2024, 11, 20);
        AdMng ad = dummyGenerator.createAd(adUser, AdSttsCd.MNG003002, startDate, endDate, aplctStart, aplctEnd);
        Assertions.assertThat(adMngRepository.findById(ad.getAdSn())).isNotEmpty();

        AplctUserMng aplctUser = dummyGenerator.createAplctUser(ad, AdPrgrsSttsCd.MNG004003, 0, LocalDateTime.now());
        Assertions.assertThat(aplctUserMngRepository.findById(aplctUser.getAplctSn())).isNotEmpty();
    }

    @Test
    @DisplayName("응모 회차 생성 테스트")
    void createAplctprgrsTest() {
        User adUser = dummyGenerator.createAdUser();
        Assertions.assertThat(userRepository.findById(adUser.getUserSn())).isNotEmpty();

        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 29);
        LocalDate aplctStart = LocalDate.of(2023, 11, 15);
        LocalDate aplctEnd = LocalDate.of(2024, 11, 20);
        AdMng ad = dummyGenerator.createAd(adUser, AdSttsCd.MNG003002, startDate, endDate, aplctStart, aplctEnd);
        Assertions.assertThat(adMngRepository.findById(ad.getAdSn())).isNotEmpty();

        AplctUserMng aplctUser = dummyGenerator.createAplctUser(ad, AdPrgrsSttsCd.MNG004003, 0, LocalDateTime.now());
        Assertions.assertThat(aplctUserMngRepository.findById(aplctUser.getAplctSn())).isNotEmpty();

        dummyGenerator.createAplctprgrs(ad, aplctUser);
        List<Aplctprgrs> aplctprgrs = aplctprgrsRepository.findByAplctUserMng_AplctSn_OrderByAdRoundsBgngYmdDesc(aplctUser.getAplctSn());

        Assertions.assertThat(aplctprgrsRepository.findByAplctUserMng_AplctSn_OrderByAdRoundsBgngYmdDesc(aplctUser.getAplctSn())).isNotEmpty();
    }
}
