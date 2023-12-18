package gw.apiserver.oms.auth.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.oms.auth.domain.AuthGroupUser;
import gw.apiserver.oms.auth.repository.AuthGroupCustomRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static gw.apiserver.oms.auth.domain.QAuthGroup.authGroup;
import static gw.apiserver.oms.auth.domain.QAuthGroupRole.authGroupRole;
import static gw.apiserver.oms.auth.domain.QAuthGroupUser.authGroupUser;

@RequiredArgsConstructor
public class AuthGroupCustomRepositoryImpl implements AuthGroupCustomRepository {
    private final JPAQueryFactory queryFactory;

    public Optional<List<AuthGroupUser>> findAuthGroupUserWithFetch(String userSn) {
        return Optional.of(
                queryFactory
                    .selectFrom(authGroupUser)
                    .join(authGroupUser.authGroup, authGroup).fetchJoin()
                    .join(authGroup.roleList, authGroupRole).fetchJoin()
                    .where(authGroupUser.user.userSn.eq(userSn))
                    .fetch()
        );
    }
}
