package gw.apiserver.oms.auth.repository;

import gw.apiserver.oms.auth.domain.AuthGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthGroupRepository extends JpaRepository<AuthGroup, String> {
}
