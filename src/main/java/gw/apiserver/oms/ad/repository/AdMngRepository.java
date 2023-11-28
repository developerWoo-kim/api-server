package gw.apiserver.oms.ad.repository;

import gw.apiserver.oms.ad.domain.AdMng;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdMngRepository extends JpaRepository<AdMng, String>, AdMngCustomRepository {
}
