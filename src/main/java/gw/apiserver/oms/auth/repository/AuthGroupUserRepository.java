package gw.apiserver.oms.auth.repository;

import gw.apiserver.oms.auth.domain.AuthGroupUser;
import gw.apiserver.oms.auth.domain.id.AuthGroupUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthGroupUserRepository extends JpaRepository<AuthGroupUser, AuthGroupUserId> {
}
