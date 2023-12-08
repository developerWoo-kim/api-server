package gw.apiserver.oms.ad.domain;

import gw.apiserver.oms.ad.domain.embedded.AdConditiId;
import gw.apiserver.oms.common.cmmcode.domain.VhclLoadweightCd;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * 광고 응모 조건 엔티티
 *
 * @create gwkim
 * @since 2023.08.03
 * @version 0.0.1
 */
@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_ad_conditi")
public class AdConditi {

    @EmbeddedId
    private AdConditiId adConditiId;            // 복합키 ( AD_SN, SORT)

//    @Enumerated(EnumType.STRING)
    private VhclLoadweightCd vhclLoadweightCd;  // 차량 적재무게 코드
    private int aplctPsbltycnt;                 // 응모 가능수
    private int allocAmt;                       // 배당 금액

    @Builder
    public AdConditi(AdConditiId adConditiId, int sort, VhclLoadweightCd vhclLoadweightCd, int aplctPsbltycnt, int allocAmt) {
        this.adConditiId = adConditiId;
        this.vhclLoadweightCd = vhclLoadweightCd;
        this.aplctPsbltycnt = aplctPsbltycnt;
        this.allocAmt = allocAmt;
    }
}
