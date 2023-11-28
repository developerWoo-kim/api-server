package gw.apiserver.oms.ad.domain;

import gw.apiserver.oms.aplct.domain.AplctUserMng;
import gw.apiserver.oms.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 광고 관리 엔티티
 *
 * @create gwkim
 * @since 2023.07.28
 * @version 0.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@Entity(name = "op_tb_ad")
public class AdMng {
    @Id
    @Column(name = "ad_sn")
    private String adSn;                    // 광고 일련번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sn")
    private User user;

    @OneToMany(mappedBy = "adMng", cascade = CascadeType.ALL)
    private List<AplctUserMng> aplctUserMngs = new ArrayList<>();   // 응모 관리

    @Enumerated(EnumType.STRING)
    private AdSttsCd adSttscd;              // 광고 상태코드 (MNG003001 : 대기, MNG003002 : 진행중, MNG003003 : 완료)
    private LocalDate adBgngYmd;            // 광고 시작일자
    private LocalDate adEndYmd;             // 광고 종료일자
    private LocalDate aplctBgngYmd;         // 응모 시작일자
    private LocalDate aplctEndYmd;          // 응모 종료일자
    private String adnm;                    // 광고명
    private String adCn;                    // 광고 내용
    private Long adAmt;                      // 광고 금액
    private String imgAtchfileSn;           // 이미지 첨부파일 일련번호
    private String orgnlAtchfileSn;         // 원본 첨부파일 일련번호
    private String useYn;                  // 사용자 여부
    private String rgtr;                    // 등록자
    private LocalDateTime regDt;            // 등록일시
    private String delYn;                   // 삭제여부

    @Builder
    public AdMng(String adSn, AdSttsCd adSttscd, LocalDate adBgngYmd, LocalDate adEndYmd, LocalDate aplctBgngYmd, LocalDate aplctEndYmd, String adnm, String adCn, Long adAmt, String useYn, String rgtr, LocalDateTime regDt, String delYn, User user) {
        this.adSn = adSn;
        this.adSttscd = adSttscd;
        this.adBgngYmd = adBgngYmd;
        this.adEndYmd = adEndYmd;
        this.aplctBgngYmd = aplctBgngYmd;
        this.aplctEndYmd = aplctEndYmd;
        this.adnm = adnm;
        this.adCn = adCn;
        this.adAmt = adAmt;
        this.useYn = useYn;
        this.rgtr = rgtr;
        this.regDt = regDt;
        this.delYn = delYn;
        this.user = user;
    }

    //== 편의 메소드 ==//

}
