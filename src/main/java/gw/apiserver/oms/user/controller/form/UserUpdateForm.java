package gw.apiserver.oms.user.controller.form;

import gw.apiserver.oms.common.cmmcode.domain.BankCd;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class UserUpdateForm {
    @NotBlank(message = "회원 정보가 누락되었습니다.")
    private String userSn;
    @NotBlank(message = "비밀번호가 누락되었습니다.")
    private String pswd;                            // 비밀번호
    @NotBlank(message = "사업자명이 누락되었습니다.")
    private String bzmnNm;                          // 사업자명
    @NotBlank(message = "대표자명이 누락되었습니다.")
    private String rprsvNm;                         // 대표자명
    @NotBlank(message = "핸드폰 번호가 누락되었습니다.")
    private String telno;                           // 전화번호
    @NotBlank(message = "팩스번호가 누락되었습니다.")
    private String fxno;                            // 팩스번호
    @NotBlank(message = "우편번호가 누락되었습니다.")
    private String zip;                             // 우편번호
    @NotBlank(message = "주소가 누락되었습니다.")
    private String addr;                            // 주소
    @NotBlank(message = "상세 주소가 누락되었습니다.")
    private String daddr;                           // 상세주소
    @NotBlank(message = "은행정보가 누락되었습니다.")
    private String bankCd;                          // 은행코드
    @NotBlank(message = "예금주명이 누락되었습니다.")
    private String dpstrNm;                         // 예금주명
    @NotBlank(message = "계좌번호가 누락되었습니다.")
    private String actno;                           // 계좌번호
    @NotBlank(message = "주 운행지역이 누락되었습니다.")
    private String mainDrivergnCd;                  // 주 운행지역 코드

    private MultipartFile leftImg;                  // 좌측 첨부파일 일련번호
    private MultipartFile rightImg;                 // 우측 첨부파일 일련번호
    private MultipartFile backImg;                  // 후방 첨부파일 일련번호
    private MultipartFile dashBoardImg;             // 계기판 첨부파일 일련번호
    private MultipartFile crcImg;                   // 차량 등록증 첨부파일 일련번호
    private MultipartFile idCardImg;                // 신분증 첨부파일 일련번호

}
