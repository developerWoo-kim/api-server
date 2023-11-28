package gw.apiserver.oms.ad.domain.dto;

import gw.apiserver.oms.common.cmmcode.domain.VhclLoadweightCd;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdConditiDto {
    private String adSn;
    private int sort;
    private VhclLoadweightCd vhclLoadweightCd;  // 차량 적재무게 코드
    private int aplctPsbltycnt;                 // 응모 가능수
    private int allocAmt;                       // 배당 금액

}
