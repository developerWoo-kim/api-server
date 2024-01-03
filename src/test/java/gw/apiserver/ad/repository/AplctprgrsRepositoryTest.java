package gw.apiserver.ad.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.dummy.DummyGenerator;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdSttsCd;
import gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd;
import gw.apiserver.oms.aplct.domain.AplctUserMng;
import gw.apiserver.oms.aplct.domain.Aplctprgrs;
import gw.apiserver.oms.aplct.repository.AplctprgrsRepository;
import gw.apiserver.oms.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static gw.apiserver.oms.aplct.domain.QAplctprgrs.aplctprgrs;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AplctprgrsRepositoryTest {
    @Autowired JPAQueryFactory queryFactory;
    @Autowired AplctprgrsRepository aplctprgrsRepository;
    @Autowired DummyGenerator dummyGenerator;

    @Test
    @DisplayName("증빙 회차 조회 테스트")
    void findProofInfo() {
        User adUser = dummyGenerator.createAdUser();
        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 29);
        LocalDate aplctStart = LocalDate.of(2023, 11, 15);
        LocalDate aplctEnd = LocalDate.of(2024, 11, 20);
        AdMng ad = dummyGenerator.createAd(adUser, AdSttsCd.MNG003002, startDate, endDate, aplctStart, aplctEnd);
        AplctUserMng aplctUser = dummyGenerator.createAplctUser(ad, AdPrgrsSttsCd.MNG004003, 0, LocalDateTime.now());
        dummyGenerator.createAplctprgrs(ad, aplctUser);
        List<Aplctprgrs> aplctprgrs = aplctprgrsRepository.findByAplctUserMng_AplctSn_OrderByAdRoundsBgngYmdDesc(aplctUser.getAplctSn());
        Assertions.assertThat(aplctprgrs).isNotEmpty();
    }

    @Test
    @DisplayName("고객의 증빙 회차 정보 조회")
    void findProofList() {
        String userSn = "USR_00002345";
        String aplctSn = "APL_00002244";

        List<Aplctprgrs> fetch = queryFactory
                .selectFrom(aplctprgrs)
                .where(aplctprgrs.aplctUserMng.aplctSn.eq(aplctSn))
                .fetch();

        Assertions.assertThat(fetch.size()).isEqualTo(2);
    }

}
