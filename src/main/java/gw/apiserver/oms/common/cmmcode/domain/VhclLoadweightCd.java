package gw.apiserver.oms.common.cmmcode.domain;

import lombok.Getter;

/**
 * 차량 적재무게 코드 VHCL_LOAD_WEIGHT_CD
 * MNG001001 : 1톤, MNG001002 : 2.5톤, MNG001003 : 3.5톤, MNG001004 : 5톤,
 * MNG001005 : 11톤, MNG001006 : 11톤 이상
 */
@Getter
public enum  VhclLoadweightCd {
    MNG001001("MNG001001", "1톤"),
    MNG001002("MNG001002", "2.5톤"),
    MNG001003("MNG001003", "3.5톤"),
    MNG001004("MNG001004", "5톤"),
    MNG001005("MNG001005", "11톤"),
    MNG001006("MNG001006", "11톤 이상");

    final String code;
    final String codeNm;

    VhclLoadweightCd(String code, String codeNm) {
        this.code = code;
        this.codeNm = codeNm;
    }
}
