package gw.apiserver.oms.ad.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.domain.AdConditi;
import gw.apiserver.oms.ad.domain.QAdConditi;
import gw.apiserver.oms.ad.domain.dto.AdMngDto;
import gw.apiserver.oms.ad.repository.AdMngCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static gw.apiserver.oms.ad.domain.QAdConditi.adConditi;
import static gw.apiserver.oms.ad.domain.QAdMng.adMng;
import static gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd.MNG004001;
import static gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd.MNG004003;
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
                .orderBy(adMng.adSn.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(adMng.count())
                .from(adMng)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();


        return new PageImpl<>(contents, pageable, count == null ? 0 : count);
    }

    public List<AdListDto.AplctCurrentStatusDto> findAplctCurrentStatus(String adSn) {
        List<AdListDto.AplctCurrentStatusDto> fetch = queryFactory
                .select(
                        Projections.constructor(AdListDto.AplctCurrentStatusDto.class,
                                comtCcmnDetailCode.code.as("code"),
                                adConditi.aplctPsbltycnt.coalesce(0).as("aplctPsbltyCnt"),
                                adConditi.vhclLoadweightCd.count().as("matchingCnt")
                        )
                )
                .from(comtCcmnDetailCode)
                .leftJoin(adConditi)
                .on(comtCcmnDetailCode.code.eq(adConditi.vhclLoadweightCd.toString())
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

}
