package gw.apiserver.oms.ad.controller.queryDto;

import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdSttsCd;
import gw.apiserver.oms.ad.domain.dto.AdMngDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AdListDto extends AdMngDto {
    private String adUserNm;        // 광고주명
    private int matchingCnt;        // 응모 인원수
    private int pasbltyCnt;         // 총 응모가능 인원 수
    List<AplctCurrentStatusDto> AplctCurrentStatus = new ArrayList<>();

    public AdListDto(AdMng adMng) {
        super(adMng);
        this.adUserNm = adMng.getUser().getUserNm();
    }

    public void addMatchingCnt(int count) {
        this.matchingCnt = count;
    }

    public void addPasbltyCnt(int count) {
        this.pasbltyCnt = count;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AplctCurrentStatusDto{
        private String code;
        private Integer aplctPsbltyCnt;
        private Long matchingCnt;
    }
}
