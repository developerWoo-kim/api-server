package gw.apiserver.oms.aplct.service;

import gw.apiserver.oms.aplct.controller.form.AdProofForm;
import gw.apiserver.oms.aplct.controller.query.AdProofDetailDto;

import java.util.List;

public interface AplctprgrsService {

    /**
     * 광고 증빙
     * @param form AdProofForm
     */
    void doAdProof(AdProofForm form);

    List<AdProofDetailDto> findAdRoofDetail(String aplctSn);
}
