package gw.apiserver.oms.ad.service;

import gw.apiserver.common.paging.SearchCondition;
import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import gw.apiserver.oms.ad.controller.queryDto.AdApplyDto;
import gw.apiserver.oms.ad.controller.queryDto.AdListDto;
import gw.apiserver.oms.ad.domain.AdConditi;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdPrefer;
import gw.apiserver.oms.ad.repository.AdMngRepository;
import gw.apiserver.oms.aplct.domain.AplctUserMng;
import gw.apiserver.oms.aplct.repository.AplctUserMngRepository;
import gw.apiserver.oms.aplct.service.AplctUserMngService;
import gw.apiserver.oms.common.cmmseq.service.ComtecopseqService;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static gw.apiserver.common.utils.date.CommonDateUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdApiServiceImpl implements AdApiService {
    private final AplctUserMngRepository aplctUserMngRepository;
    private final AdMngRepository adMngRepository;
    private final UserRepository userRepository;
    private final AplctUserMngService aplctUserMngService;
    private final ComtecopseqService comtecopseqService;

    @Override
    public Page<AdListDto> searchAdPagingList(SearchCondition condition, Pageable pageable) {
        Page<AdListDto> adPageList = adMngRepository.findAdPageList(condition, pageable);

        for (AdListDto adList : adPageList.getContent()) {
            List<AdListDto.AplctCurrentStatusDto> aplctCurrentStatus = adMngRepository.findAplctCurrentStatus(adList.getAdSn());
            adList.setAplctCurrentStatus(aplctCurrentStatus);

            int matchingCnt = 0;
            int psbltyCnt = 0;
            for (AdListDto.AplctCurrentStatusDto currentStatus : aplctCurrentStatus) {
                matchingCnt += currentStatus.getMatchingCnt();
                psbltyCnt += currentStatus.getAplctPsbltyCnt();
            }

            adList.addMatchingCnt(matchingCnt);
            adList.addPasbltyCnt(psbltyCnt);
        }

        return adPageList;
    }

    @Override
    @Transactional
    public void applyAd(String adSn, String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));  // 회원정보 조회
        AdMng adMng = adMngRepository.findById(adSn).orElseThrow(
                () -> new IllegalArgumentException("광고가 존재하지 않습니다."));
        // 광고 응모 중복 체크
        if(aplctUserMngService.applyDuplicationCheck(adSn, user.getUserSn()))
            throw new GlobalApiException(CommonError.AD_APPLY_DUPLICATION);
        // 응모 기간 검증
        if(!isWithinDateRange(LocalDate.now(), adMng.getAplctBgngYmd(), adMng.getAplctEndYmd()))
            throw new GlobalApiException(CommonError.AD_NO_APPLY_PERIOD);
        // 이미 진행중인 광고가 있는지 체크
        if(aplctUserMngService.anyCurrentlyAdRunningCheck(user.getUserSn()))
            throw new GlobalApiException(CommonError.AD_ALREADY_EXISTS_RUNNING);
        // 응모조건에 해당하는지
        if(adMngRepository.findAdConditi(adSn).stream()
                .noneMatch(adConditi -> adConditi.getVhclLoadweightCd().equals(user.getVhclLoadweightCd())))
            throw new GlobalApiException(CommonError.AD_NOT_MATCH_CONDITION);


        AplctUserMng aplctUser = AplctUserMng.createAplctUser(comtecopseqService.generateUUID_APL(), adMng, user);
        aplctUserMngRepository.save(aplctUser);
    }

    @Override
    public List<AdApplyDto> findApplyAdList(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalApiException(CommonError.USER_NOT_FOUND));

        List<AdMng> applyAdList = adMngRepository.findApplyAdList(user.getUserSn());

        if(applyAdList.isEmpty())
            throw new GlobalApiException(CommonError.AD_APPLY_LIST_NOT_FOUND);

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

        return adApplyDtoList;
    }

}
