package gw.apiserver.oms.ad.domain.dto;

import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdSttsCd;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AdMngDto {
    private String id;
    private AdSttsCd adSttscd;              // 광고 상태코드 (MNG003001 : 대기, MNG003002 : 진행중, MNG003003 : 완료)
    private LocalDate adBgngYmd;            // 광고 시작일자
    private LocalDate adEndYmd;             // 광고 종료일자
    private LocalDate aplctBgngYmd;         // 응모 시작일자
    private LocalDate aplctEndYmd;          // 응모 종료일자
    private String adnm;                    // 광고명
    private String adUserNm;        // 광고주명
    private String adCn;                    // 광고 내용
    private Long adAmt;                      // 광고 금액
    private String imgAtchfileSn;           // 이미지 첨부파일 일련번호
    private String orgnlAtchfileSn;         // 원본 첨부파일 일련번호
    private String useYn;                  // 사용자 여부
    private String rgtr;                    // 등록자
    private LocalDateTime regDt;            // 등록일시
    private String delYn;                   // 삭제여부

    public AdMngDto(String id, AdSttsCd adSttscd, LocalDate adBgngYmd, LocalDate adEndYmd, LocalDate aplctBgngYmd, LocalDate aplctEndYmd, String adnm, String adCn, Long adAmt, String imgAtchfileSn, String orgnlAtchfileSn, String useYn, String rgtr, LocalDateTime regDt, String delYn) {
        this.id = id;
        this.adSttscd = adSttscd;
        this.adBgngYmd = adBgngYmd;
        this.adEndYmd = adEndYmd;
        this.aplctBgngYmd = aplctBgngYmd;
        this.aplctEndYmd = aplctEndYmd;
        this.adnm = adnm;
        this.adCn = adCn;
        this.adAmt = adAmt;
        this.imgAtchfileSn = imgAtchfileSn;
        this.orgnlAtchfileSn = orgnlAtchfileSn;
        this.useYn = useYn;
        this.rgtr = rgtr;
        this.regDt = regDt;
        this.delYn = delYn;
    }

    public AdMngDto(AdMng adMng) {
        this.id = adMng.getAdSn();
        this.adUserNm = adMng.getUser().getUserNm();
        this.adSttscd = adMng.getAdSttscd();
        this.adBgngYmd = adMng.getAdBgngYmd();
        this.adEndYmd = adMng.getAdEndYmd();
        this.aplctBgngYmd = adMng.getAplctBgngYmd();
        this.aplctEndYmd = adMng.getAplctEndYmd();
        this.adnm = adMng.getAdnm();
        this.adCn = adMng.getAdCn();
        this.adAmt = adMng.getAdAmt();
        this.imgAtchfileSn = adMng.getImgAtchfileSn();
        this.orgnlAtchfileSn = adMng.getOrgnlAtchfileSn();
        this.useYn = adMng.getUseYn();
        this.rgtr = adMng.getRgtr();
        this.regDt = adMng.getRegDt();
        this.delYn = adMng.getDelYn();
    }
}
