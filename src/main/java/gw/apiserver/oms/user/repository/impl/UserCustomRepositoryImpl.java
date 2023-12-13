package gw.apiserver.oms.user.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.oms.user.repository.UserCustomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final JPAQueryFactory queryFactory;


}
