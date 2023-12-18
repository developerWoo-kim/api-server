package gw.apiserver.oms.auth.repository;

import gw.apiserver.oms.auth.domain.AuthGroupUser;

import java.util.List;
import java.util.Optional;

public interface AuthGroupCustomRepository {

    /**
     * 회원 권한 객체 그래프로 조회 With Fetch
     * @param userSn 회원 일련번호
     * @return Optional<List<AuthGroupUser>>
     */
    Optional<List<AuthGroupUser>> findAuthGroupUserWithFetch(String userSn);
}
