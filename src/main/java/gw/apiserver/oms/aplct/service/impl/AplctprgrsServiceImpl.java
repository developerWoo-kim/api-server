package gw.apiserver.oms.aplct.service.impl;

import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import gw.apiserver.oms.aplct.controller.form.AdProofForm;
import gw.apiserver.oms.aplct.controller.query.AdProofDetailDto;
import gw.apiserver.oms.aplct.domain.Aplctprgrs;
import gw.apiserver.oms.aplct.repository.AplctprgrsRepository;
import gw.apiserver.oms.aplct.service.AplctprgrsService;
import gw.apiserver.oms.cfm.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AplctprgrsServiceImpl implements AplctprgrsService {
    private final AplctprgrsRepository aplctprgrsRepository;
    private final CommonFileService commonFileService;

    @Override
    public void doAdProof(AdProofForm form){
        String aplctprgrsSn = form.getAplctprgrsSn();
        Aplctprgrs aplctprgrs = aplctprgrsRepository.findById(aplctprgrsSn)
                .orElseThrow(() -> new GlobalApiException(CommonError.APLCT_PRGRS_NOT_FOUND));

        String leftSn = commonFileService.save("adProof", form.getEvdncLeftAtchfile());
        String rightSn = commonFileService.save("adProof", form.getEvdncRightAtchfile());
        String backSn = commonFileService.save("adProof", form.getEvdncBackAtchfile());
        String pnlSn = commonFileService.save("adProof", form.getEvdncPnlAtchfile());

        aplctprgrs.setEvdncLeftAtchfileSn(leftSn);
        aplctprgrs.setEvdncRightAtchfileSn(rightSn);
        aplctprgrs.setEvdncBackAtchfileSn(backSn);
        aplctprgrs.setEvdncPnlAtchfileSn(pnlSn);
        aplctprgrs.setPnlKm(form.getPnlKm());
        aplctprgrs.setDriveKm(3504);
        aplctprgrs.setEvdncYn("Y");
        aplctprgrs.setEvdncRegDt(LocalDateTime.now());
    }

    @Override
    public List<AdProofDetailDto> findAdRoofDetail(String aplctSn) {
        List<Aplctprgrs> findAdProofDetail = aplctprgrsRepository.findByAplctUserMng_AplctSn_OrderByAdRoundsBgngYmdDesc(aplctSn);
        if(findAdProofDetail.isEmpty()) throw new GlobalApiException(CommonError.APLCT_PRGRS_NOT_FOUND);

        List<AdProofDetailDto> list = new ArrayList<>();
        for(Aplctprgrs aplctprgrs : findAdProofDetail) {
            list.add(AdProofDetailDto.createAdProofDetail(aplctprgrs));
        }

        return list;
    }
}
