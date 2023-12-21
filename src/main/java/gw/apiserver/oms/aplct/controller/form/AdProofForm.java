package gw.apiserver.oms.aplct.controller.form;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AdProofForm {
    @NotBlank(message = "진행 회차 일련번호가 누락되었습니다.")
    private String aplctprgrsSn; // 응모 진행 회차 일련번호
    @NotNull(message = "차량 좌측 사진은 필수입니다.")
    private MultipartFile evdncLeftAtchfile;         // 증빙 좌측 첨부파일
    @NotNull(message = "차량 우측 사진은 필수입니다.")
    private MultipartFile evdncRightAtchfile;        // 증빙 우측 첨부파일
    @NotNull(message = "차량 후방 사진은 필수입니다.")
    private MultipartFile evdncBackAtchfile;         // 증빙 후방 첨부파일
    @NotNull(message = "계기판 사진은 필수입니다.")
    private MultipartFile evdncPnlAtchfile;          // 증빙 계기판 첨부파일
    private long pnlKm;                         // 계기판 키로수

    @Builder
    public AdProofForm(String aplctprgrsSn, MultipartFile evdncLeftAtchfile, MultipartFile evdncRightAtchfile, MultipartFile evdncBackAtchfile, MultipartFile evdncPnlAtchfile, long pnlKm) {
        this.aplctprgrsSn = aplctprgrsSn;
        this.evdncLeftAtchfile = evdncLeftAtchfile;
        this.evdncRightAtchfile = evdncRightAtchfile;
        this.evdncBackAtchfile = evdncBackAtchfile;
        this.evdncPnlAtchfile = evdncPnlAtchfile;
        this.pnlKm = pnlKm;
    }
}
