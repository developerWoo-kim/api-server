package gw.apiserver.oms.ad.controller.queryDto;

import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.dto.AdMngDto;
import gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdMatchListDto extends AdMngDto {
    private String aplctSn;
    private String adPrgrsSttscd;
    private LocalDateTime applyDateTime;

    public AdMatchListDto(AdMng adMng, String aplctSn, AdPrgrsSttsCd adPrgrsSttsCd, LocalDateTime applyDateTime) {
        super(adMng);
        this.aplctSn = aplctSn;
        this.adPrgrsSttscd = adPrgrsSttsCd.getCodeNm();
        this.applyDateTime = applyDateTime;
    }
}
