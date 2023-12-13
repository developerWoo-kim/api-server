package gw.apiserver.oms.common.api.service;

import gw.apiserver.oms.common.api.domain.ApiMng;
import gw.apiserver.oms.common.api.repository.ApiMngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApiMngServiceImpl {
    private final ApiMngRepository apiMngRepository;
    @Cacheable(value = "apiReqUri")
    public List<ApiMng> findAllApi() {
        return apiMngRepository.findAll();
    }
}
