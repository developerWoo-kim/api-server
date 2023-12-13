package gw.apiserver.oms.common.api.repository;

import gw.apiserver.oms.common.api.domain.ApiMng;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiMngRepository extends JpaRepository<ApiMng, String> {
}
