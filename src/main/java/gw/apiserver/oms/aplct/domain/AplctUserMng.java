package gw.apiserver.oms.aplct.domain;

import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 응모자 관리 엔티티
 *
 * @create gwkim
 * @since 2023.07.28
 * @version 0.0.1
 */
@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_aplct_user")
public class AplctUserMng {
    @Id
    @Column(name = "aplct_sn")
    private String aplctSn;                 // 응모 일련번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_sn")
    private AdMng adMng;                    // 광고 관리

    @OneToMany(mappedBy = "aplctUserMng")
    private List<Aplctprgrs> aplctprgrs = new ArrayList<>(); // 광고별 응모자 진행회차 관리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sn")
    private User user;

    @Enumerated(value = EnumType.STRING)
    private AdPrgrsSttsCd adPrgrsSttscd;    // 광고 진행 상태코드 (MNG004001 : 광고 매칭전, MNG004002 : 광고 준비, MNG004003 : 광고 진행, MNG004004 : 광고 완료, MNG004005 : 광고 중단)
    private String rappYn;                  // 랩핑 여부
    private String rappLeftAtchfileSn;      // 랩핑 좌측 첨부파일 일련번호
    private String rappRightAtchfileSn;     // 랩핑 우측 첨부파일 일련번호
    private String rappBackAtchfileSn;       // 랩핑 후방 첨부파일 일련번호
    private String rappPnlAtchfileSn;       // 랩핑 계기판 첨부파일 일련번호
    private Integer rappPnlKm;                 // 랩핑 계기판 키로수
    private LocalDateTime aplctRegDt;       // 응모 등록일시
    private String rappRjctCn;              // 랩핑 반려 내용
    private String rappIdfrSn;              // 랩핑 확인자 일련번호
    private LocalDateTime rappRegDt;        // 랩핑 등록일시
    private String rappUserSn;              // 랩핑 사용자 일련번호

    @Builder
    public AplctUserMng(String aplctSn, AdMng adMng, User user, AdPrgrsSttsCd adPrgrsSttscd, String rappYn,
                        String rappLeftAtchfileSn, String rappRightAtchfileSn,
                        String rappBackAtchfileSn, String rappPnlAtchfileSn, Integer rappPnlKm,
                        LocalDateTime aplctRegDt, String rappRjctCn, String rappIdfrSn, LocalDateTime rappRegDt, String rappUserSn) {
        this.aplctSn = aplctSn;
        this.adMng = adMng;
        this.user = user;
        this.adPrgrsSttscd = adPrgrsSttscd;
        this.rappYn = rappYn;
        this.rappLeftAtchfileSn = rappLeftAtchfileSn;
        this.rappRightAtchfileSn = rappRightAtchfileSn;
        this.rappBackAtchfileSn = rappBackAtchfileSn;
        this.rappPnlAtchfileSn = rappPnlAtchfileSn;
        this.rappPnlKm = rappPnlKm;
        this.aplctRegDt = aplctRegDt;
        this.rappRjctCn = rappRjctCn;
        this.rappIdfrSn = rappIdfrSn;
        this.rappRegDt = rappRegDt;
        this.rappUserSn = rappUserSn;
    }

//    @Builder
//    public AplctUserMng(String aplctSn,AdMng adMng, User user, AdPrgrsSttsCd adPrgrsSttsCd, String rappYn, Integer rappPnlKm, LocalDateTime aplctRegDt, LocalDateTime rappRegDt) {
//        this.aplctSn = aplctSn;
//        this.adMng = adMng;
//        this.user = user;
//        this.adPrgrsSttscd = adPrgrsSttsCd;
//        this.rappYn = rappYn;
//        this.rappPnlKm = rappPnlKm;
//        this.aplctRegDt = aplctRegDt;
//        this.rappRegDt = rappRegDt;
//    }
}
