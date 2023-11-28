package gw.apiserver.oms.common.cmmseq.repository;

import gw.apiserver.oms.common.cmmseq.domain.Comtecopseq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComtecopseqRepository extends JpaRepository<Comtecopseq, String> {
    Comtecopseq findByTableName(String tableName);
}
