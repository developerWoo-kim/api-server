package gw.apiserver.ad;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.domain.*;

import gw.apiserver.oms.ad.repository.AdMngRepository;
import gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd;
import gw.apiserver.oms.aplct.domain.AplctUserMng;
import gw.apiserver.oms.common.cmmcode.domain.QComtCcmnDetailCode;
import gw.apiserver.oms.user.domain.QUser;
import gw.apiserver.oms.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static gw.apiserver.oms.ad.domain.QAdConditi.adConditi;
import static gw.apiserver.oms.ad.domain.QAdMng.adMng;
import static gw.apiserver.oms.ad.domain.QAdPrefer.adPrefer;
import static gw.apiserver.oms.aplct.domain.QAplctUserMng.aplctUserMng;
import static gw.apiserver.oms.common.cmmcode.domain.QComtCcmnDetailCode.comtCcmnDetailCode;
import static gw.apiserver.oms.user.domain.QUser.user;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdMngRepositoryTest {
    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    AdMngRepository adMngRepository;

    @Test
    @DisplayName("광고 페이징 리스트 조회")
    public void findAdPageList() {
        Pageable pageable = PageRequest.of(0, 20);

        List<AdListDto> contents = queryFactory
                .select(Projections.constructor(AdListDto.class,
                        adMng
                ))
                .from(adMng)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(adMng.count())
                .from(adMng)
                .join(adMng.user, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();


        Page<AdListDto> adListDtos = new PageImpl<>(contents, pageable, count);


        System.out.println(adListDtos);

    }

    @Test
    @DisplayName("응모 현황 조회")
    public void findApplicantStatus() {
        String adSn = "AD_0000007";
        List<Tuple> mng001 = queryFactory
                .select(
                        comtCcmnDetailCode.code,
                        adConditi.aplctPsbltycnt.coalesce(0).as("aplctPsbltyCnt"),
                        adConditi.vhclLoadweightCd.count().as("matchingCnt")
                )
                .from(comtCcmnDetailCode)
                .leftJoin(adConditi)
                .on(comtCcmnDetailCode.code.eq(adConditi.vhclLoadweightCd.toString())
                        , adConditi.adConditiId.adSn.eq(adSn))
                .where(comtCcmnDetailCode.codeId.eq("MNG001"))
                .groupBy(comtCcmnDetailCode.code)
                .fetch();

        System.out.println(mng001);
    }

    @Test
    @DisplayName("응모 가능자 수 조회")
    public void findPossibilityCount() {
        String adSn = "AD_0000011";
        Integer count = queryFactory
                .select(adConditi.aplctPsbltycnt.sum())
                .from(adConditi)
                .where(adConditi.adConditiId.adSn.eq(adSn))
                .fetchOne();

        System.out.println(count);

        Assertions.assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("응모자 수 조회")
    public void findApplicantCount() {
        String adSn = "AD_0000007";
        Long count = queryFactory
                .select(aplctUserMng.count())
                .from(aplctUserMng)
                .where(aplctUserMng.adMng.adSn.eq(adSn))
                .fetchOne();

        System.out.println(count);

        Assertions.assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("응모 기간 검증 : 신청 일자가 응모 종료 날짜보다 적은지")
    public void adApplyEndDtCheckTest() {
        AdMng ad = adMngRepository.findById("AD_0000012").orElseThrow();
        LocalDate aplctBgngYmd = ad.getAplctBgngYmd();
        LocalDate aplctEndYmd = ad.getAplctEndYmd();
        LocalDate localDate = LocalDate.of(2023, 07, 1);

        Assertions.assertThat(isWithinDateRange(localDate, aplctBgngYmd, aplctEndYmd)).isTrue();
    }

    private static boolean isWithinDateRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    @Test
    @DisplayName("회원이 응모한 광고 목록 조회")
    public void findApplyAdListTest() {
        String userSn = "USR_00000021";

        User findUser = queryFactory
                .selectFrom(user)
                .where(
                        user.userSn.eq(userSn)
                )
                .fetchOne();

        Optional<List<AdMng>> fetch1 = Optional.of(queryFactory
                .select(adMng)
                .from(adMng)
                .join(adMng.aplctUserMngs, aplctUserMng)
                .where(
                        aplctUserMng.user.userSn.eq(userSn),
                        aplctUserMng.adPrgrsSttscd.eq(AdPrgrsSttsCd.MNG004001)
                )
                .fetch());

        Optional<List<AdMng>> fetch2 = Optional.ofNullable(queryFactory
                .select(adMng)
                .from(adMng)
                .join(adMng.aplctUserMngs, aplctUserMng)
                .where(
                        aplctUserMng.user.userSn.eq(userSn),
                        aplctUserMng.adPrgrsSttscd.eq(AdPrgrsSttsCd.MNG004001)
                )
                .fetch());


        List<AdMng> fetch = queryFactory
                .select(adMng)
                .from(adMng)
                .join(adMng.aplctUserMngs, aplctUserMng)
                .where(
                        aplctUserMng.user.userSn.eq(userSn),
                        aplctUserMng.adPrgrsSttscd.eq(AdPrgrsSttsCd.MNG004001)
                )
                .fetch();

        for (AdMng mng : fetch) {
            String adSn = mng.getAdSn();
            Optional<AdConditi> first = adMngRepository.findAdConditi(adSn).stream()
                    .filter(adConditi -> adConditi.getVhclLoadweightCd().equals(findUser.getVhclLoadweightCd()))
                    .findFirst();
            if(first.isPresent()) {
                AdConditi findAdConditi = first.get();
                System.out.println(adSn + " : "+ findAdConditi.getAllocAmt());
            }

        }

        Assertions.assertThat(fetch.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("응모한 광고가 없을때 테스트")
    public void applyAdListEmptyTest() {
        String userSn = "USR_000000213";

        List<AdMng> fetch = queryFactory
                .select(adMng)
                .from(adMng)
                .join(adMng.aplctUserMngs, aplctUserMng)
                .where(
                        aplctUserMng.user.userSn.eq(userSn),
                        aplctUserMng.adPrgrsSttscd.eq(AdPrgrsSttsCd.MNG004001)
                )
                .fetch();

        Assertions.assertThat(fetch).isEmpty();
    }

    @Test
    @DisplayName("우대조건 조회")
    public void findAdPreferCondition() {
        String adSn = "AD_0000011";

        List<AdPrefer> fetch = queryFactory
                .selectFrom(adPrefer)
                .where(
                        adPrefer.id.adSn.eq(adSn)
                )
                .fetch();

        Assertions.assertThat(fetch.size()).isEqualTo(5);
    }
}
