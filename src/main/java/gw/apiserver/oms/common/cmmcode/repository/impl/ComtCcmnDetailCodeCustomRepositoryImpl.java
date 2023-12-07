package gw.apiserver.oms.common.cmmcode.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gw.apiserver.oms.common.cmmcode.controller.dto.CommonCodeDetailDto;
import gw.apiserver.oms.common.cmmcode.domain.ComtCcmnDetailCode;
import gw.apiserver.oms.common.cmmcode.repository.ComtCcmnDetailCodeCustomRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static gw.apiserver.oms.common.cmmcode.domain.QComtCcmnDetailCode.comtCcmnDetailCode;

@RequiredArgsConstructor
public class ComtCcmnDetailCodeCustomRepositoryImpl implements ComtCcmnDetailCodeCustomRepository {
    private final JPAQueryFactory queryFactory;

    /**
     * 공통 코드 조회
     * @param codeId String 코드 아이디
     * @return List<ComtCcmnDetailCode>
     */
    @Override
    public List<CommonCodeDetailDto> findCmmCodeDetail(String codeId) {
        return queryFactory
                .select(Projections.fields(CommonCodeDetailDto.class,
                        comtCcmnDetailCode.code,
                        comtCcmnDetailCode.codeId,
                        comtCcmnDetailCode.codeNm
                ))
                .from(comtCcmnDetailCode)
                .where(
                        comtCcmnDetailCode.useAt.eq("Y"),
                        comtCcmnDetailCode.codeId.eq(codeId)
                )
                .fetch();
    }
}
