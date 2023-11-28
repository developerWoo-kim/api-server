package gw.apiserver.oms.aplct.service;

import gw.apiserver.oms.aplct.domain.Aplctprgrs;
import gw.apiserver.oms.aplct.repository.AplctprgrsRepository;
import gw.apiserver.oms.common.cmmseq.service.ComtecopseqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AplctprgrsService {
    private final AplctprgrsRepository aplctprgrsRepository;
    private final ComtecopseqService comtecopseqService;

    @Transactional(readOnly = false)
    public void save(Aplctprgrs aplctprgrs) {
        String aplctprgrsSn = comtecopseqService.generateUUID(); // UUID 생성


    }
}
