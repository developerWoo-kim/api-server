package gw.apiserver.oms.aplct.service;

public interface AplctUserMngService {
    /**
     * 응모 중복 체크
     * @param adSn String
     * @param userSn String
     * @return boolean
     */
    boolean applyDuplicationCheck(String adSn, String userSn);

    /**
     * 회원이 현재 진행중인 광고가 있는지 체크
     * @param userSn String
     * @return Long count
     */
    boolean anyCurrentlyAdRunningCheck(String userSn);
}
