package gw.apiserver.oms.user.repository;

import gw.apiserver.oms.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository {
    Optional<User> findByUserId(String userId);


}
