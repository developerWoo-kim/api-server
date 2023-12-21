package gw.apiserver.oms.ad.domain.dto;

import gw.apiserver.oms.ad.domain.AdPrefer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdPreferDto {
    private String prefCondinm;
    private int sort;

    public AdPreferDto(AdPrefer adPrefer) {
        this.prefCondinm = adPrefer.getId().getPrefCondinm();
        this.sort = adPrefer.getSort();
    }
}
