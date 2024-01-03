package gw.apiserver.oms.aplct.controller.query;

import gw.apiserver.oms.aplct.domain.Aplctprgrs;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 광고 증빙 상세 내역 Dto
 */
@Data
@NoArgsConstructor
public class AdProofDetailDto {
    private String aplctprgrsSn;                // 응모진행 일련번호
    private String aplctSn;
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
    private LocalDateTime evdncRegDt;           // 증빙 등록 일시

    @Builder
    public AdProofDetailDto(String aplctprgrsSn, String aplctSn, int evdncRounds, String evdncYn, LocalDate evdncBgngYmd, LocalDate evdncEndYmd, String evdncLeftAtchfileSn, String evdncRightAtchfileSn, String evdncBackAtchfileSn, String evdncPnlAtchfileSn, long pnlKm, long driveKm, LocalDateTime evdncRegDt) {
        this.aplctprgrsSn = aplctprgrsSn;
        this.aplctSn = aplctSn;
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
        this.evdncRegDt = evdncRegDt;
    }

    public static AdProofDetailDto createAdProofDetail(Aplctprgrs aplctprgrs) {
        AdProofDetailDto detail = new AdProofDetailDto();
        detail.setAplctprgrsSn(aplctprgrs.getAplctprgrsSn());
        detail.setAplctSn(aplctprgrs.getAplctUserMng().getAplctSn());
        detail.setEvdncRounds(aplctprgrs.getEvdncRounds());
        detail.setEvdncYn(aplctprgrs.getEvdncYn());
        detail.setEvdncBgngYmd(aplctprgrs.getEvdncBgngYmd());
        detail.setEvdncEndYmd(aplctprgrs.getEvdncEndYmd());
        detail.setEvdncLeftAtchfileSn(aplctprgrs.getEvdncLeftAtchfileSn());
        detail.setEvdncRightAtchfileSn(aplctprgrs.getEvdncRightAtchfileSn());
        detail.setEvdncBackAtchfileSn(aplctprgrs.getEvdncBackAtchfileSn());
        detail.setEvdncPnlAtchfileSn(aplctprgrs.getEvdncPnlAtchfileSn());
        detail.setPnlKm(aplctprgrs.getPnlKm());
        detail.setDriveKm(aplctprgrs.getDriveKm());
        detail.setEvdncRegDt(aplctprgrs.getEvdncRegDt());
        return detail;
    }
}
