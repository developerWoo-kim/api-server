package gw.apiserver.oms.aplct.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.oms.aplct.repository.AplctUserMngCustomRepository;
import lombok.RequiredArgsConstructor;

import static gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd.MNG004001;
import static gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd.MNG004003;
import static gw.apiserver.oms.aplct.domain.QAplctUserMng.aplctUserMng;
import static gw.apiserver.oms.user.domain.QUser.user;

@RequiredArgsConstructor
public class AplctUserMngCustomRepositoryImpl implements AplctUserMngCustomRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 회원이 현재 진행중인 광고가 있는지 체크
     * @param userSn String
     * @return Long count
     */
    @Override
    public Long anyCurrentlyAdRunningCheck(String userSn) {
        return queryFactory
                .select(aplctUserMng.count())
                .from(aplctUserMng)
                .leftJoin(user).on(aplctUserMng.user.userSn.eq(user.userSn))
                .where(
                        aplctUserMng.adPrgrsSttscd.eq(MNG004001)
                                .or(aplctUserMng.adPrgrsSttscd.eq(MNG004003)),
                        user.userSn.eq(userSn)
                )
                .fetchOne();
    }
}
