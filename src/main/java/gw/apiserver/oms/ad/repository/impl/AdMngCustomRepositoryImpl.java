package gw.apiserver.oms.ad.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.oms.ad.controller.queryDto.AdMatchListDto;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.domain.AdConditi;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdPrefer;
import gw.apiserver.oms.ad.repository.AdMngCustomRepository;
import gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd;
import gw.apiserver.oms.aplct.domain.AplctUserMng;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static gw.apiserver.oms.ad.domain.QAdConditi.adConditi;
import static gw.apiserver.oms.ad.domain.QAdMng.adMng;
import static gw.apiserver.oms.ad.domain.QAdPrefer.adPrefer;
import static gw.apiserver.oms.aplct.domain.QAplctUserMng.aplctUserMng;
import static gw.apiserver.oms.common.cmmcode.domain.QComtCcmnDetailCode.comtCcmnDetailCode;
import static gw.apiserver.oms.user.domain.QUser.user;

@RequiredArgsConstructor
public class AdMngCustomRepositoryImpl implements AdMngCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdListDto> findAdPageList(SearchCondition searchCondition, Pageable pageable) {
        List<AdListDto> contents = queryFactory
                .select(Projections.constructor(AdListDto.class,
                        adMng
                ))
                .from(adMng)
                .join(adMng.user, user).fetchJoin()
                .orderBy(adMng.adSn.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(adMng.count())
                .from(adMng)
                .join(adMng.user, user).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();


        return new PageImpl<>(contents, pageable, count == null ? 0 : count);
    }

    @Override
    public List<AdListDto> findAdList(int count, String lastId) {
        return queryFactory
                .select(Projections.constructor(AdListDto.class,
                        adMng
                ))
                .from(adMng)
                .join(adMng.user, user).fetchJoin()
                .where(
                        leLastId(lastId)
                )
                .orderBy(adMng.adSn.desc())
                .limit(count)
                .fetch();
    }

    @Override
    public Optional<AdMng> hasNextData(String lastId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(adMng)
                        .where(leLastId(lastId))
                        .limit(1)
                        .fetchOne()
        );
    }

    private BooleanExpression leLastId(String lastId) {
        if (lastId == null) {
            return null;
        }

        return adMng.adSn.lt(lastId);
    }

    public List<AdListDto.AplctCurrentStatusDto> findAplctCurrentStatus(String adSn) {
        List<AdListDto.AplctCurrentStatusDto> fetch = queryFactory
                .select(
                        Projections.constructor(AdListDto.AplctCurrentStatusDto.class,
                                comtCcmnDetailCode.code.as("code"),
                                comtCcmnDetailCode.codeNm.as("codeNm"),
                                adConditi.aplctPsbltycnt.coalesce(0).as("aplctPsbltyCnt"),
                                adConditi.vhclLoadweightCd.count().as("matchingCnt"),
                                adConditi.allocAmt.coalesce(0).as("allocAmt")
                        )
                )
                .from(comtCcmnDetailCode)
                .leftJoin(adConditi)
                .on(comtCcmnDetailCode.code.eq(adConditi.vhclLoadweightCd)
                        , adConditi.adConditiId.adSn.eq(adSn))
                .where(comtCcmnDetailCode.codeId.eq("MNG001"))
                .groupBy(comtCcmnDetailCode.code)
                .fetch();


        System.out.println(fetch);

        return fetch;
    }

    @Override
    public List<AdConditi> findAdConditi(String adSn) {
        return queryFactory
                .select(adConditi)
                .from(adConditi)
                .where(adConditi.adConditiId.adSn.eq(adSn))
                .fetch();
    }

    @Override
    public List<AdPrefer> findAdPrefer(String adSn) {
        return queryFactory
                .selectFrom(adPrefer)
                .where(
                        adPrefer.id.adSn.eq(adSn)
                )
                .fetch();
    }

    @Override
    public List<AdMng> findApplyAdList(String userSn) {

        return queryFactory
                .select(adMng)
                .from(adMng)
                .join(adMng.aplctUserMngs, aplctUserMng)
                .where(
                        aplctUserMng.user.userSn.eq(userSn),
                        aplctUserMng.adPrgrsSttscd.eq(AdPrgrsSttsCd.MNG004001)
                )
                .fetch();
    }

    @Override
    public List<AdMatchListDto> findMatchAdList(int count, String lastId, String userSn) {
        return queryFactory
                .select(Projections.constructor(AdMatchListDto.class,
                        adMng,
                        aplctUserMng.aplctSn,
                        aplctUserMng.adPrgrsSttscd,
                        aplctUserMng.aplctRegDt
                ))
                .from(adMng)
                .join(adMng.aplctUserMngs, aplctUserMng).fetchJoin()
                .where(
                        aplctUserMng.user.userSn.eq(userSn)
//                        aplctUserMng.adPrgrsSttscd.eq(AdPrgrsSttsCd.MNG004001).or(aplctUserMng.adPrgrsSttscd.eq(AdPrgrsSttsCd.MNG004002))
                )
                .orderBy(aplctUserMng.aplctRegDt.desc())
                .limit(count)
                .fetch();
    }

    @Override
    public Optional<AplctUserMng> hasApplyAdNextData(String lastId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(aplctUserMng)
                        .where(leApplyAdLastId(lastId))
                        .limit(1)
                        .fetchOne()
        );
    }

    private BooleanExpression leApplyAdLastId(String lastId) {
        if (lastId == null) {
            return null;
        }

        return aplctUserMng.aplctSn.lt(lastId);
    }

}
