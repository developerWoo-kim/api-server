package gw.apiserver.oms.user.repository;

import gw.apiserver.oms.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository {
}
