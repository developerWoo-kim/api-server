package gw.apiserver.oms.cfm.repository;

import gw.apiserver.oms.cfm.domain.CommonFileMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonFileMasterRepository extends JpaRepository<CommonFileMaster, String> {
}
