package gw.apiserver.oms.ad.controller.queryDto;

import gw.apiserver.oms.ad.domain.AdConditi;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdPrefer;
import gw.apiserver.oms.ad.domain.dto.AdConditiDto;
import gw.apiserver.oms.ad.domain.dto.AdMngDto;
import gw.apiserver.oms.ad.domain.dto.AdPreferDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AdApplyDto extends AdMngDto {
    private int userAllocAmt;
    private List<AdConditiDto> conditiList = new ArrayList<>();   // 응모 조건
    private List<AdPreferDto> preferList = new ArrayList<>();       // 우대 조건
    public AdApplyDto(AdMng adMng) {
        super(adMng);
    }


    //== 편의 메소드 ==//
    public void addUserAllocAmt(int allocAmt) {
        this.userAllocAmt = allocAmt;
    }
    public void addConditi(AdConditi adConditi) {
        this.conditiList.add(new AdConditiDto(adConditi));
    }

    public void addPrefer(AdPrefer adPrefer) {
        this.preferList.add(new AdPreferDto(adPrefer));
    }

}
