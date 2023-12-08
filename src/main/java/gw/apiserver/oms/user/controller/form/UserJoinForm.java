package gw.apiserver.oms.user.controller.form;

import gw.apiserver.oms.common.cmmcode.domain.MainDrivergnCd;
import gw.apiserver.oms.common.cmmcode.domain.VhclLoadweightCd;
import gw.apiserver.oms.common.cmmcode.domain.VhclTypeCd;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserJoinForm {
    private String userId;                          // 사용자 아이디
    private String userNm;                          // 사용자명
    private String pswd;                            // 비밀번호
    private String bzmnNm;                          // 사업자명
    private String bzmnNo;                          // 사업자 번호
    private String rprsvNm;                         // 대표자명
    private String vhclNo;                          // 차량번호
    private VhclLoadweightCd vhclLoadweightCd;      // 차량 적재무게 코드
    private VhclTypeCd vhclTypeCd;                  // 차량 종류 코드
    private String mainDrivergnCd;          // 주 운행지역 코드
}