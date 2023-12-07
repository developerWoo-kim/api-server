package gw.apiserver.oms.common.cmmcode.repository;

import gw.apiserver.oms.common.cmmcode.controller.dto.CommonCodeDetailDto;

import java.util.List;

public interface ComtCcmnDetailCodeCustomRepository {

    /**
     * 공통 코드 조회
     * @param codeId String 코드 아이디
     * @return List<ComtCcmnDetailCode>
     */
    List<CommonCodeDetailDto> findCmmCodeDetail(String codeId);
}
