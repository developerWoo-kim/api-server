package gw.apiserver.oms.aplct.repository;

import gw.apiserver.oms.aplct.domain.AplctUserMng;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AplctUserMngRepository extends JpaRepository<AplctUserMng, String> {
}
