package gw.apiserver.oms.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gw.apiserver.oms.auth.domain.AuthGroupUser;
import gw.apiserver.oms.common.cmmcode.domain.BankCd;
import gw.apiserver.oms.common.cmmcode.domain.MainDrivergnCd;
import gw.apiserver.oms.common.cmmcode.domain.VhclLoadweightCd;
import gw.apiserver.oms.common.cmmcode.domain.VhclTypeCd;
import gw.apiserver.oms.user.controller.form.UserJoinForm;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 회원 관리
 *
 * @create gwkim
 * @since 2023.07.28
 * @version 0.0.1
 */
@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_user")
public class User {

    @Id
    @Column(name = "user_sn")
    private String userSn;                          // 사용자 일련번호

    @JsonIgnore
    @OneToOne(mappedBy = "user" ,fetch = FetchType.LAZY)
    private AuthGroupUser authGroupUser;

    private String userId;                          // 사용자 아이디
    private String userNm;                        // 사용자명
    private String pswd;                            // 비밀번호
    private String eml;                             // 이메일
    private String rprsvNm;                         // 대표자명
    private String bzmnNm;                          // 사업자명
    private String bzmnNo;                          // 사업자 번호
    private String telno;                           // 전화번호
    private String fxno;                            // 팩스번호
    private String zip;                             // 우편번호
    private String addr;                            // 주소
    private String daddr;                           // 상세주소

    @Enumerated(EnumType.STRING)
    private BankCd bankCd;                          // 은행코드
    private String dpstrNm;                         // 예금주명
    private String actno;                           // 계좌번호
    private String vhclNo;                          // 차량번호

    @Enumerated(EnumType.STRING)
    private VhclLoadweightCd vhclLoadweightCd;      // 차량 적재무게 코드

    @Enumerated(EnumType.STRING)
    private VhclTypeCd vhclTypeCd;                  // 차량 종류 코드

    private String mainDrivergnCd;          // 주 운행지역 코드

    private String avrDriveBgnghr;                  // 평균 운행 시작시간
    private String avrDriveEndhr;                   // 평균 운행 종료시간
    private String leftAtchfileSn;                  // 좌측 첨부파일 일련번호
    private String rightAtchfileSn;                 // 우측 첨부파일 일련번호
    private String backAtchfileSn;                  // 후방 첨부파일 일련번호
    private String pnlAtchfileSn;                   // 계기판 첨부파일 일련번호
    private String bzmnrgstrAtchfileSn;             // 사업자등록증 첨부파일 일련번호
    private String vhclRgstrAtchfileSn;             // 차량 등록증 첨부파일 일련번호
    private String idcardAtchfileSn;                // 신분증 첨부파일 일련번호
    private String frghtCrtfcAtchfileSn;            // 화물운송 자격증 첨부파일 일련번호
    private String tmprPswdYn;                      // 임시 비밀번호 여부
    private String rgtr;                            // 등록자
    private LocalDateTime regDt;                    // 등록일시
    private String delYn;                           // 삭제 여부
    private LocalDateTime whdwlDt;                  // 탈퇴일시
    private String mbrMdfcnYn;                      // 회원수정여부
    private LocalDateTime mbrMdfcnDt;               // 회원수정일시
    private String aprvYn;                          // 승인여부
    private String blackmbrYn;                      // 불량회원 여부
    private String mdfcnIdntyYn;                    // 수정 확인 여부
    private String memo;                            // 메모
    private String profileAtchfileSn;               // 프로필 첨부파일 일련번호

    @Builder
    public User(String userSn, String userId, String userNm, String pswd, String eml, String bzmnNm, String bzmnNo, String telno, String fxno, String zip, String addr, String daddr, BankCd bankCd, String dpstrNm, String actno, String vhclNo, VhclLoadweightCd vhclLoadweightCd, VhclTypeCd vhclTypeCd, String mainDrivergnCd, String avrDriveBgnghr, String avrDriveEndhr, String leftAtchfileSn, String rightAtchfileSn, String backAtchfileSn, String pnlAtchfileSn, String bzmnrgstrAtchfileSn, String vhclRgstrAtchfileSn, String idcardAtchfileSn, String frghtCrtfcAtchfileSn, String tmprPswdYn, String rgtr, LocalDateTime regDt, String delYn, LocalDateTime whdwlDt, String mbrMdfcnYn, LocalDateTime mbrMdfcnDt, String aprvYn, String blackmbrYn, String mdfcnIdntyYn, String memo, String profileAtchfileSn) {
        this.userSn = userSn;
        this.userId = userId;
        this.userNm = userNm;
        this.pswd = pswd;
        this.eml = eml;
        this.bzmnNm = bzmnNm;
        this.bzmnNo = bzmnNo;
        this.telno = telno;
        this.fxno = fxno;
        this.zip = zip;
        this.addr = addr;
        this.daddr = daddr;
        this.bankCd = bankCd;
        this.dpstrNm = dpstrNm;
        this.actno = actno;
        this.vhclNo = vhclNo;
        this.vhclLoadweightCd = vhclLoadweightCd;
        this.vhclTypeCd = vhclTypeCd;
        this.mainDrivergnCd = mainDrivergnCd;
        this.avrDriveBgnghr = avrDriveBgnghr;
        this.avrDriveEndhr = avrDriveEndhr;
        this.leftAtchfileSn = leftAtchfileSn;
        this.rightAtchfileSn = rightAtchfileSn;
        this.backAtchfileSn = backAtchfileSn;
        this.pnlAtchfileSn = pnlAtchfileSn;
        this.bzmnrgstrAtchfileSn = bzmnrgstrAtchfileSn;
        this.vhclRgstrAtchfileSn = vhclRgstrAtchfileSn;
        this.idcardAtchfileSn = idcardAtchfileSn;
        this.frghtCrtfcAtchfileSn = frghtCrtfcAtchfileSn;
        this.tmprPswdYn = tmprPswdYn;
        this.rgtr = rgtr;
        this.regDt = regDt;
        this.delYn = delYn;
        this.whdwlDt = whdwlDt;
        this.mbrMdfcnYn = mbrMdfcnYn;
        this.mbrMdfcnDt = mbrMdfcnDt;
        this.aprvYn = aprvYn;
        this.blackmbrYn = blackmbrYn;
        this.mdfcnIdntyYn = mdfcnIdntyYn;
        this.memo = memo;
        this.profileAtchfileSn = profileAtchfileSn;
    }

    //== 생성 메서드 ==//
    /**
     * 회원 가입
     * @param form UserJoinForm
     * @param userSn String
     * @return User
     */
    public static User createUser(UserJoinForm form, String userSn) {
        return new User().builder()
                .userSn(userSn)
                .userId(form.getUserId())
                .userNm(form.getUserNm())
                .pswd(form.getPswd())
                .bzmnNm(form.getBzmnNm())
                .bzmnNo(form.getBzmnNo())
                .zip(form.getZip())
                .addr(form.getAddr())
                .daddr(form.getDaddr())
                .vhclNo(form.getVhclNo())
                .vhclLoadweightCd(form.getVhclLoadweightCd())
                .vhclTypeCd(form.getVhclTypeCd())
                .mainDrivergnCd(conversionMainDriverCd(form.getMainDrivergnCd()))
                .build();
    }

    //== 편의 메서드 ==//

    /**
     * 주 운행지역 문자열 콤마 기준으로 코드 문자열 변환
     * 수도권,충청권 -> USR002001,USR002002
     *
     * @param MainDriverCdStr String
     * @return String
     */
    public static String conversionMainDriverCd(String MainDriverCdStr) {
        String[] cdStrArr = MainDriverCdStr.split(",");
        String mainDriverCd = "";
        for (int i = 0; i < cdStrArr.length; i++) {
            MainDrivergnCd byCodeNm = MainDrivergnCd.getByCodeNm(cdStrArr[i]);

            mainDriverCd += byCodeNm.getCode();

            if(i < cdStrArr.length-1) {
                mainDriverCd += ",";
            }
        }

        return mainDriverCd;
    }
}
