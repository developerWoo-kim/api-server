package gw.apiserver.oms.common.cmmseq.service;

import gw.apiserver.oms.common.cmmseq.domain.Comtecopseq;
import gw.apiserver.oms.common.cmmseq.repository.ComtecopseqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공통 시퀀스 관리 서비스
 *
 * @create gwkim
 * @since 2023.08.01
 * @version 0.0.1
 */
@Service
@RequiredArgsConstructor
public class ComtecopseqService {
    private final ComtecopseqRepository comtecopseqRepository;
    /**
     * 회원 시퀀스
     *
     * @return
     */
    @Transactional
    public String generateUUID_USR() {
        String tableName = "USR_ID"; // SEQ 참조 값
        String prefix = "USR_";      // UUID 앞에 고정적으로 붙이고자 하는 설정 값 지정
        int cipers = 8;             // prefix를 제외한 아이디의 길이 지정

        // 시퀀스 테이블 조회하여 증감시킨 후 포맷 생성
        Comtecopseq comtecopseq = comtecopseqRepository.findByTableName(tableName);

        String formattedId = String.format("%0" + cipers + "d", comtecopseq.getNextId());

        comtecopseq.incrementId();

        return prefix + formattedId;
    }

    /**
     * 광고 관리 시퀀스
     *
     * @return
     */
    @Transactional
    public String generateUUID_AD() {
        String tableName = "AD_ID"; // SEQ 참조 값
        String prefix = "AD_";      // UUID 앞에 고정적으로 붙이고자 하는 설정 값 지정
        int cipers = 7;             // prefix를 제외한 아이디의 길이 지정

        // 시퀀스 테이블 조회하여 증감시킨 후 포맷 생성
        Comtecopseq comtecopseq = comtecopseqRepository.findByTableName(tableName);

        String formattedId = String.format("%0" + cipers + "d", comtecopseq.getNextId());

        comtecopseq.incrementId();

        return prefix + formattedId;
    }

    /**
     * 응모자관리 시퀀스
     *
     * @return
     */
    @Transactional
    public String generateUUID_APL() {
        String tableName = "APL_ID"; // SEQ 참조 값
        String prefix = "APL_";      // UUID 앞에 고정적으로 붙이고자 하는 설정 값 지정
        int cipers = 8;             // prefix를 제외한 아이디의 길이 지정

        // 시퀀스 테이블 조회하여 증감시킨 후 포맷 생성
        Comtecopseq comtecopseq = comtecopseqRepository.findByTableName(tableName);

        String formattedId = String.format("%0" + cipers + "d", comtecopseq.getNextId());

        comtecopseq.incrementId();
        return prefix + formattedId;
    }

    /**
     * 광고별응모진행회차관리 시퀀스
     *
     * @return
     */
    @Transactional
    public String generateUUID() {
        String tableName = "APR_ID"; // SEQ 참조 값
        String prefix = "APR_";      // UUID 앞에 고정적으로 붙이고자 하는 설정 값 지정
        int cipers = 10;             // prefix를 제외한 아이디의 길이 지정

        // 시퀀스 테이블 조회하여 증감시킨 후 포맷 생성
        Comtecopseq comtecopseq = comtecopseqRepository.findByTableName(tableName);

        String formattedId = String.format("%0" + cipers + "d", comtecopseq.getNextId());

        comtecopseq.incrementId();

        return prefix + formattedId;
    }

    /**
     * 알림톡 로그 시퀀스
     *
     * @return
     */
    @Transactional
    public String generateUUID_ALT() {
        String tableName = "ALT_ID"; // SEQ 참조 값
        String prefix = "ALT_";      // UUID 앞에 고정적으로 붙이고자 하는 설정 값 지정
        int cipers = 8;             // prefix를 제외한 아이디의 길이 지정

        // 시퀀스 테이블 조회하여 증감시킨 후 포맷 생성
        Comtecopseq comtecopseq = comtecopseqRepository.findByTableName(tableName);

        String formattedId = String.format("%0" + cipers + "d", comtecopseq.getNextId());

        comtecopseq.incrementId();

        return prefix + formattedId;
    }
}
