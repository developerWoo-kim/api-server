package gw.apiserver.ad.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.oms.ad.controller.queryDto.AdMatchListDto;
import gw.apiserver.oms.ad.domain.AdMng;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static gw.apiserver.oms.ad.domain.QAdMng.adMng;
import static gw.apiserver.oms.aplct.domain.QAplctUserMng.aplctUserMng;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdRepositoryTest {
    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("회원이 응모한 광고 리스트 조회")
    void findApplyAdList() {
        String userSn = "USR_00000023";

        List<AdMng> fetch =
                queryFactory
                .selectFrom(adMng)
                .join(adMng.aplctUserMngs, aplctUserMng).fetchJoin()
                .where(aplctUserMng.user.userSn.eq(userSn))
                .fetch();


        Assertions.assertThat(fetch.size()).isEqualTo(6);
    }

    @Test
    @DisplayName("회원이 응모한 광고 리스트 조회 - DTO 반환 테스트")
    void findApplyAdListReturnDtoTest() {
        String userSn = "USR_00000023";

        List<AdMatchListDto> fetch = queryFactory
                .select(Projections.constructor(AdMatchListDto.class,
                        adMng,
                        aplctUserMng.aplctSn,
                        aplctUserMng.adPrgrsSttscd,
                        aplctUserMng.aplctRegDt
                ))
                .from(adMng)
                .join(adMng.aplctUserMngs, aplctUserMng).fetchJoin()
                .where(aplctUserMng.user.userSn.eq(userSn))
                .fetch();



        Assertions.assertThat(fetch.size()).isEqualTo(6);
    }
}
