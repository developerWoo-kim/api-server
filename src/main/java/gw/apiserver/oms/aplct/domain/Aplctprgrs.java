package gw.apiserver.oms.aplct.domain;

import gw.apiserver.oms.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 광고별 응모자 진행 회차 관리
 *
 * @create gwkim
 * @since 2023.07.28
 * @version 0.0.1
 */
@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_aplctprgrs")
public class Aplctprgrs {
    @Id
    @Column(name = "aplctprgrsSn")
    private String aplctprgrsSn;                // 응모진행 일련번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplct_sn")
    private AplctUserMng aplctUserMng;          // 응모 관리

    private String adSn;                        // 광고 일련번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sn")
    private User user;                          // 사용자 일련번호

    private int evdncRounds;                    // 증빙 회차
    private String evdncYn;                     // 증빙 여부
    private LocalDate evdncBgngYmd;             // 증빙 시작일자
    private LocalDate evdncEndYmd;              // 증빙 종료일자
    private String evdncLeftAtchfileSn;         // 증빙 좌측 첨부파일 일련번호
    private String evdncRightAtchfileSn;        // 증빙 우측 첨부파일 일련번호
    private String evdncBackAtchfileSn;         // 증빙 후방 첨부파일 일련번호
    private String evdncPnlAtchfileSn;          // 증빙 계기판 첨부파일 일련번호
    private long pnlKm;                         // 계기판 키로수
    private long driveKm;                       // 운행 키로수
    private String memo;                        // 메모
    private LocalDateTime evdncRegDt;           // 증빙 등록 일시
    private String clclnYn;                     // 정산 여부
    private LocalDateTime clclnRegDt;           // 정산 등록 일시
    private LocalDate adRoundsBgngYmd;          // 광고 회차 시작일자
    private LocalDate adRoundsEndYmd;           // 광고 회차 종료일자

    @Builder
    public Aplctprgrs(String aplctprgrsSn, AplctUserMng aplctUserMng, String adSn, User user, int evdncRounds, String evdncYn, LocalDate evdncBgngYmd,
                      LocalDate evdncEndYmd, String evdncLeftAtchfileSn, String evdncRightAtchfileSn, String evdncBackAtchfileSn, String evdncPnlAtchfileSn,
                      long pnlKm, long driveKm, String memo, LocalDateTime evdncRegDt, String clclnYn, LocalDateTime clclnRegDt, LocalDate adRoundsBgngYmd,
                      LocalDate adRoundsEndYmd) {
        this.aplctprgrsSn = aplctprgrsSn;
        this.aplctUserMng = aplctUserMng;
        this.adSn = adSn;
        this.user = user;
        this.evdncRounds = evdncRounds;
        this.evdncYn = evdncYn;
        this.evdncBgngYmd = evdncBgngYmd;
        this.evdncEndYmd = evdncEndYmd;
        this.evdncLeftAtchfileSn = evdncLeftAtchfileSn;
        this.evdncRightAtchfileSn = evdncRightAtchfileSn;
        this.evdncBackAtchfileSn = evdncBackAtchfileSn;
        this.evdncPnlAtchfileSn = evdncPnlAtchfileSn;
        this.pnlKm = pnlKm;
        this.driveKm = driveKm;
        this.memo = memo;
        this.evdncRegDt = evdncRegDt;
        this.clclnYn = clclnYn;
        this.clclnRegDt = clclnRegDt;
        this.adRoundsBgngYmd = adRoundsBgngYmd;
        this.adRoundsEndYmd = adRoundsEndYmd;
    }

    //== 편의 메소드 ==//
    /**
     * 기존 진행 회차가 있는 경우 회차+1
     * @param aplctprgrs(Optional<List<Aplctprgrs>>)
     */
    public void incrementRound(Optional<List<Aplctprgrs>> aplctprgrs) {
        if(aplctprgrs.isPresent()) {
            this.evdncRounds = aplctprgrs.get().size() + 1;
        } else {
            this.evdncRounds = 1;
        }
    }
    /**
     * 증빙 시작일자, 종료일자 생성
     */
    public void createEvdncYmd() {
        LocalDate evdnDate = LocalDate.from(YearMonth.now().atDay(1));
        this.evdncBgngYmd = evdnDate;
        this.evdncEndYmd = evdnDate.plusWeeks(1);
    }
    /**
     * 광고 회차 시작일자, 종료일자 생성
     */
    public void createAdRoundYmd() {
        LocalDate targetDate = YearMonth.now().minusMonths(1).atDay(1);
        this.adRoundsBgngYmd = LocalDate.from(LocalDate.from(targetDate));
        this.adRoundsEndYmd = LocalDate.from(targetDate.withDayOfMonth(targetDate.lengthOfMonth()));
    }

    /**
     * 증빙 시작일자, 종료일자 생성 2
     */
    public void createEvdncYmd2(LocalDate nowDate) {
        this.evdncBgngYmd = nowDate.withDayOfMonth(1);
        this.evdncEndYmd = nowDate.plusDays(7);
    }
    /**
     * 광고 회차 시작일자, 종료일자 생성 2
     */
    public void createAdRoundYmd2(LocalDate nowDate) {
        this.adRoundsBgngYmd = nowDate.withDayOfMonth(1);
        this.adRoundsEndYmd = nowDate.withDayOfMonth(nowDate.lengthOfMonth());
    }

    /**
     * 배치 수행 시 당월 입력된 회차 정보가 있을 경우 false
     *
     * @param opAplctprgrs
     * @return
     */
    public static boolean validationEvdnc(Optional<List<Aplctprgrs>> opAplctprgrs) {
        boolean checker = true;
        LocalDate firstDate = LocalDate.from(LocalDate.from(YearMonth.now().atDay(1)));
        if(opAplctprgrs.isEmpty()) {
            List<Aplctprgrs> aplctprgrs = opAplctprgrs.get();
            List<Aplctprgrs> collect = aplctprgrs.stream()
                    .filter(aplctprgrs1 -> aplctprgrs1.getEvdncBgngYmd().isAfter(firstDate) ||
                            aplctprgrs1.getEvdncBgngYmd().isEqual(firstDate))
                    .collect(Collectors.toList());

            if(collect.size() > 0) {
                checker = false;
            }
        }
        return checker;
    }
}
