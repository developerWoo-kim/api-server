package gw.apiserver.oms.aplct.repository;

public interface AplctUserMngCustomRepository {
    /**
     * 회원이 현재 진행중인 광고가 있는지 체크
     * @param userSn String
     * @return Long count
     */
    Long anyCurrentlyAdRunningCheck(String userSn);
}
