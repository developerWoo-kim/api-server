package gw.apiserver.ad;

import com.querydsl.jpa.impl.JPAQueryFactory;

import gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd;
import gw.apiserver.oms.aplct.domain.AplctUserMng;
import gw.apiserver.oms.aplct.domain.QAplctUserMng;
import gw.apiserver.oms.user.domain.QUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd.MNG004001;
import static gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd.MNG004003;
import static gw.apiserver.oms.aplct.domain.QAplctUserMng.aplctUserMng;
import static gw.apiserver.oms.user.domain.QUser.user;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AplctprgrsRepositoryTest {
    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("현재 진행중인 광고가 있는지 체크")
    void anyCurrentlyAdRunningCheck() {
        Long count = queryFactory
                .select(aplctUserMng.count())
                .from(aplctUserMng)
                .leftJoin(user).on(aplctUserMng.user.userSn.eq(user.userSn))
                .where(
                        aplctUserMng.adPrgrsSttscd.eq(MNG004001)
                                .or(aplctUserMng.adPrgrsSttscd.eq(MNG004003)),
                        user.userSn.eq("USR_00000006")
                )
                .fetchOne();
    }
}
