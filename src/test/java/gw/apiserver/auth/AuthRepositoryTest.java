package gw.apiserver.auth;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.oms.auth.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static gw.apiserver.oms.auth.domain.QAuthGroup.authGroup;
import static gw.apiserver.oms.auth.domain.QAuthGroupRole.authGroupRole;
import static gw.apiserver.oms.auth.domain.QAuthGroupUser.authGroupUser;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthRepositoryTest {
    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("권한 묶어서 조회")
    void findUserGroupAuth() {
        String userSn = "admin1";

        List<AuthGroupUser> fetch = queryFactory
                .selectFrom(authGroupUser)
                .join(authGroupUser.authGroup, authGroup).fetchJoin()
                .join(authGroup.roleList, authGroupRole).fetchJoin()
                .where(authGroupUser.user.userSn.eq(userSn))
                .fetch();


        System.out.println(fetch);

    }
}
