package gw.apiserver.oms.aplct.domain;

import lombok.Getter;

/**
 * 광고 진행 상태 코드
 * (MNG004001 : 광고 매칭전, MNG004002 : 광고 준비, MNG004003 : 광고 진행, MNG004004 : 광고 완료, MNG004005 : 광고 중단)
 *
 */
@Getter
public enum AdPrgrsSttsCd {
    MNG004001("MNG004001", "매칭 진행중"),
    MNG004002("MNG004002", "광고 준비"),
    MNG004003("MNG004003", "광고 진행중"),
    MNG004004("MNG004004", "완료"),
    MNG004005("MNG004005", "중단");

    final String code;
    final String codeNm;

    AdPrgrsSttsCd(String code, String codeNm) {
        this.code = code;
        this.codeNm = codeNm;
    }
}
