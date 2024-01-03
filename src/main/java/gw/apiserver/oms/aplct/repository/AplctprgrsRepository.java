package gw.apiserver.oms.aplct.repository;

import gw.apiserver.oms.aplct.domain.Aplctprgrs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AplctprgrsRepository extends JpaRepository<Aplctprgrs, String>, AplctprgrsCustomRepository {
    /**
     * 증빙 회차 조회
     * @param aplctSn String
     * @return Optional<List<Aplctprgrs>>
     */
    List<Aplctprgrs> findByAplctUserMng_AplctSn_OrderByAdRoundsBgngYmdDesc(String aplctSn);
}
