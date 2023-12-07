package gw.apiserver.oms.common.cmmcode.repository;

import gw.apiserver.oms.common.cmmcode.domain.ComtCcmnDetailCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComtCcmnDetailCodeRepository extends JpaRepository<ComtCcmnDetailCode, String>, ComtCcmnDetailCodeCustomRepository {
}
