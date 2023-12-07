package gw.apiserver.oms.common.cmmcode.domain;

import lombok.Getter;

import java.util.Arrays;

/**
 * 주 운행지역 코드 MAIN_DRIVERGN_CD
 * USR002001 : 수도권, USR002002 : 충청권, USR002003 : 경상권, USR002004 : 전라권, USR002005 : 강원권
 */
@Getter
public enum MainDrivergnCd {
    USR002001("USR002001", "수도권"),
    USR002002("USR002002", "충청권"),
    USR002003("USR002003", "경상권"),
    USR002004("USR002004", "전라권"),
    USR002005("USR002005", "강원권");

    final String code;
    final String codeNm;
    MainDrivergnCd(String code, String codeNm) {
        this.code = code;
        this.codeNm = codeNm;
    }

    public static MainDrivergnCd getByCodeNm(String codeNm) {
        for (MainDrivergnCd value : MainDrivergnCd.values()) {
            if (value.codeNm.equals(codeNm)) {
                return value;
            }
        }
        // 찾지 못했을 경우, 예외 또는 기본값을 반환할 수 있습니다.
        throw new IllegalArgumentException("No enum constant with codeNm: " + codeNm);
    }
}
