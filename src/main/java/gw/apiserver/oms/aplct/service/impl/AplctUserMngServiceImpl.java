package gw.apiserver.oms.aplct.service.impl;

import gw.apiserver.oms.aplct.repository.AplctUserMngRepository;
import gw.apiserver.oms.aplct.service.AplctUserMngService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AplctUserMngServiceImpl implements AplctUserMngService {
    private final AplctUserMngRepository aplctUserMngRepository;

    /**
     * 응모 중복 체크
     * @param adSn String
     * @param userSn String
     * @return boolean
     */
    @Override
    public boolean applyDuplicationCheck(String adSn, String userSn) {
        long count = aplctUserMngRepository.countAplctUserMngByAdMng_AdSnAndUser_UserSn(adSn, userSn);

        boolean result = false;
        if(count > 0) {
            result = true;
        }

        return result;
    }

    /**
     * 회원이 현재 진행중인 광고가 있는지 체크
     * @param userSn String
     * @return Long count
     */
    @Override
    public boolean anyCurrentlyAdRunningCheck(String userSn) {
        Long count = aplctUserMngRepository.anyCurrentlyAdRunningCheck(userSn);

        boolean result = false;
        if(count > 0) {
            result = true;
        }

        return result;
    }

}
