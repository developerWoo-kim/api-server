package gw.apiserver.dummy;

import gw.apiserver.oms.ad.domain.AdConditi;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdSttsCd;
import gw.apiserver.oms.ad.domain.embedded.AdConditiId;
import gw.apiserver.oms.ad.repository.AdConditiRepository;
import gw.apiserver.oms.ad.repository.AdMngRepository;
import gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd;
import gw.apiserver.oms.aplct.domain.AplctUserMng;
import gw.apiserver.oms.aplct.domain.Aplctprgrs;
import gw.apiserver.oms.aplct.repository.AplctUserMngRepository;
import gw.apiserver.oms.aplct.repository.AplctprgrsRepository;
import gw.apiserver.oms.auth.domain.AuthGroup;
import gw.apiserver.oms.auth.domain.AuthGroupUser;
import gw.apiserver.oms.auth.domain.id.AuthGroupUserId;
import gw.apiserver.oms.auth.repository.AuthGroupRepository;
import gw.apiserver.oms.auth.repository.AuthGroupUserRepository;
import gw.apiserver.oms.common.cmmcode.domain.BankCd;
import gw.apiserver.oms.common.cmmcode.domain.MainDrivergnCd;
import gw.apiserver.oms.common.cmmcode.domain.VhclLoadweightCd;
import gw.apiserver.oms.common.cmmcode.domain.VhclTypeCd;
import gw.apiserver.oms.common.cmmseq.repository.ComtecopseqRepository;
import gw.apiserver.oms.common.cmmseq.service.ComtecopseqService;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.YearMonth;
import java.util.Random;

@Service
public class DummyGenerator {
    @Autowired private UserRepository userRepository;
    @Autowired private AuthGroupRepository authGroupRepository;
    @Autowired private AuthGroupUserRepository authGroupUserRepository;
    @Autowired private AdMngRepository adMngRepository;
    @Autowired private AdConditiRepository adConditiRepository;
    @Autowired private AplctUserMngRepository aplctUserMngRepository;
    @Autowired private AplctprgrsRepository aplctprgrsRepository;
    @Autowired private ComtecopseqService comtecopseqService;
    @Autowired private PasswordEncoder passwordEncoder;

    public User createDriverUser() {
        return createUser("AUTH_000000000000006", "화물기사", "qwer1234!", VhclLoadweightCd.MNG001001, VhclTypeCd.MNG002001, MainDrivergnCd.USR002001);
    }
    public User createAdUser() {
        return createUser("AUTH_000000000000003", "광고주", "qwer1234!", null, null, null);
    }

    /**
     * 회원 생성
     *
     * 권한 : AUTH_000000000000003 : ROLE_AD_MNG : 광고주
     * 권한 : AUTH_000000000000004 : ROLE_AD_EMP : 광고대행 직원
     * 권한 : AUTH_000000000000005 : ROLE_RAPP   : 랩핑사
     * 권한 : AUTH_000000000000006 : ROLE_DRIVER : 화물기사
     */
    public User createUser(String authrtGroupSn, String name, String pwd, VhclLoadweightCd vhclLoadweightCd, VhclTypeCd vhclTypeCd,
                           MainDrivergnCd mainDrivergnCd) {
        // 화물차 회원 생성
        User user = new User();
        if(authrtGroupSn.equals("AUTH_000000000000006")) user = driverUserBuilder(name, pwd, vhclLoadweightCd, vhclTypeCd, mainDrivergnCd);
        if(authrtGroupSn.equals("AUTH_000000000000003")) user = adMngUserBuilder(name, pwd);

        User saveUser = userRepository.save(user);
        AuthGroup authGroup = authGroupRepository.findById(authrtGroupSn).orElseThrow();

        // 권한 그룹 생성
        AuthGroupUser authGroupUser = new AuthGroupUser();
        authGroupUser.setId(new AuthGroupUserId(saveUser.getUserSn(),authrtGroupSn));
        authGroupUser.setUser(saveUser);
        authGroupUser.setAuthGroup(authGroup);
        authGroupUserRepository.save(authGroupUser);

        return saveUser;
    }

    /**
     * 광고 생성
     * @param user User 광고주 회원
     * @param adStart LocalDate 광고 시작일자
     * @param adEnd LocalDate 광고 종료일자
     * @param aplctStart LocalDate 신청 시작일자
     * @param aplctEnd LocalDate 신청 종료일자
     * @return AdMng
     */
    public AdMng createAd(User user, AdSttsCd adSttsCd, LocalDate adStart, LocalDate adEnd, LocalDate aplctStart, LocalDate aplctEnd) {
        AdMng adMng = adMngRepository.save(
                adBuilder(
                        adSttsCd, adStart, adEnd,
                        aplctStart, aplctEnd, "테스트 광고_" + createRandomString(),
                        "광고 내용", 450000L, "Y", "더미 생성기", LocalDateTime.now(), "N", user)
        );

        AdConditi adConditi = new AdConditi().builder()
                .adConditiId(new AdConditiId(adMng.getAdSn(),1))
                .aplctPsbltycnt(10)
                .vhclLoadweightCd(VhclLoadweightCd.MNG001001)
                .allocAmt(150000)
                .build();
        adConditiRepository.save(adConditi);

        AdConditi adConditi2 = new AdConditi().builder()
                .adConditiId(new AdConditiId(adMng.getAdSn(),2))
                .aplctPsbltycnt(5)
                .vhclLoadweightCd(VhclLoadweightCd.MNG001002)
                .allocAmt(250000)
                .build();
        adConditiRepository.save(adConditi2);

        AdConditi adConditi3 = new AdConditi().builder()
                .adConditiId(new AdConditiId(adMng.getAdSn(),3))
                .aplctPsbltycnt(20)
                .vhclLoadweightCd(VhclLoadweightCd.MNG001004)
                .allocAmt(450000)
                .build();
        adConditiRepository.save(adConditi3);

        return adMng;
    }

    /**
     * 응모자 생성
     * @return AplctUserMng
     */
    public AplctUserMng createAplctUser(AdMng adMng, AdPrgrsSttsCd adPrgrsSttsCd, int rappRnlKm, LocalDateTime regDateTime) {
        User user = createUser("AUTH_000000000000006", "화물기사", "qwer1234!", VhclLoadweightCd.MNG001001, VhclTypeCd.MNG002001, MainDrivergnCd.USR002001);

        return aplctUserMngRepository.save(aplctUserMngBuilder(
                adMng, user, adPrgrsSttsCd, "Y",
                "FLE_0000000000000217", "FLE_0000000000000218",
                "FLE_0000000000000219", "FLE_0000000000000220",
                rappRnlKm, regDateTime)
        );
    }

    /**
     * 응모 회차 생성
     */
    public void createAplctprgrs(AdMng adMng, AplctUserMng aplctUserMng) {
        LocalDate adBgngYmd = adMng.getAdBgngYmd();
        LocalDate adEndYmd = adMng.getAdEndYmd();

        Period between = Period.between(adBgngYmd, adEndYmd);
        int months = between.getMonths();

        for(int i = 0; i < months; i++) {
            // 응모자 진행 회차 엔티티 세팅
            Aplctprgrs aplctprgrs = new Aplctprgrs().builder()
                    .aplctprgrsSn(comtecopseqService.generateUUID())
                    .aplctUserMng(aplctUserMng)
                    .adSn(adMng.getAdSn())
                    .user(aplctUserMng.getUser())
                    .clclnYn("N")
                    .evdncYn("N")
                    .build();

            // 응모 진행 내역이 없으면 round는 무조건 1, 있으면 기존 응모 진행 회차 + 1
            aplctprgrs.setEvdncRounds(i+1);
            LocalDate from1 = LocalDate.from(adBgngYmd.atStartOfDay());
            aplctprgrs.setEvdncBgngYmd(from1);
            aplctprgrs.setEvdncEndYmd(from1.plusWeeks(1));
            LocalDate from2 = LocalDate.from(adBgngYmd.atStartOfDay());
            aplctprgrs.setAdRoundsBgngYmd(from2);
            aplctprgrs.setAdRoundsEndYmd(LocalDate.from(from2.withDayOfMonth(from2.lengthOfMonth())));

            aplctprgrsRepository.save(aplctprgrs);
            adBgngYmd = adBgngYmd.plusMonths(1);
        }

    }



    /**
     * 화물기사 빌더
     *
     * @param userName
     * @param pswd
     * @param vhclLoadweightCd
     * @param vhclTypeCd
     * @param mainDrivergnCd
     * @return
     * @throws NoSuchAlgorithmException
     */
    private User driverUserBuilder (String userName, String pswd, VhclLoadweightCd vhclLoadweightCd, VhclTypeCd vhclTypeCd,
                                   MainDrivergnCd mainDrivergnCd) {
        String id = createRandomString();
        String pwd = passwordEncoder.encode(pswd);
        String name = userName + "_" + id;
        return new User().builder()
                .userSn(comtecopseqService.generateUUID_USR())
                .userId(id)
                .userNm(name).pswd(pwd).eml("테스트이메일").bzmnNm("테스트사업자").bzmnNo("테스트 사업자번호")
                .telno("01022913734").fxno("더미").zip("05643").addr("서울 송파구 가락로33길 3").daddr("332").bankCd(BankCd.USR001001).dpstrNm("더미").actno("4314314")

                .vhclNo("346거1521").vhclLoadweightCd(vhclLoadweightCd).vhclTypeCd(vhclTypeCd).mainDrivergnCd(mainDrivergnCd.toString()).avrDriveBgnghr("06:30").avrDriveEndhr("19:00")
                .leftAtchfileSn("FLE_0000000000000048").rightAtchfileSn("FLE_0000000000000049").backAtchfileSn("FLE_0000000000000050")
                .pnlAtchfileSn("FLE_0000000000000051").bzmnrgstrAtchfileSn("FLE_0000000000000047").vhclRgstrAtchfileSn("FLE_0000000000000052")
                .idcardAtchfileSn("FLE_0000000000000053").frghtCrtfcAtchfileSn("FLE_0000000000000054")

                .rgtr("더미생성기").regDt(LocalDateTime.now()).delYn("N").mbrMdfcnYn("N")
                .aprvYn("Y").blackmbrYn("N").mdfcnIdntyYn("N")
                .build();
    }

    /**
     * 광고주 빌더
     *
     * @param userName
     * @param pswd
     * @return
     * @throws NoSuchAlgorithmException
     */
    private User adMngUserBuilder (String userName, String pswd) {
        String id = createRandomString();
        String pwd = passwordEncoder.encode(pswd);
        String name = userName + "_" + id;
        return new User().builder()
                .userSn(comtecopseqService.generateUUID_USR())
                .userId(id)
                .userNm(name).pswd(pwd).eml("테스트이메일").bzmnNm("테스트사업자").bzmnNo("테스트 사업자번호")
                .telno("테스트 번호").fxno("더미").zip("05643").addr("서울 송파구 가락로33길 3").daddr("332").bankCd(BankCd.USR001001)
                .dpstrNm("더미").actno("4314314")
                .bzmnrgstrAtchfileSn("FLE_0000000000000047")
                .rgtr("더미생성기").regDt(LocalDateTime.now()).delYn("N").mbrMdfcnYn("N")
                .aprvYn("Y").blackmbrYn("N").mdfcnIdntyYn("N")
                .build();
    }

    /**
     * 광고 빌더
     *
     * @param adSttscd
     * @param adBgngYmd
     * @param adEndYmd
     * @param aplctBgngYmd
     * @param aplctEndYmd
     * @param adnm
     * @param adCn
     * @param adAmt
     * @param useYn
     * @param rgtr
     * @param regDt
     * @param delYn
     * @param user
     * @return
     */
    private AdMng adBuilder(AdSttsCd adSttscd, LocalDate adBgngYmd, LocalDate adEndYmd, LocalDate aplctBgngYmd, LocalDate aplctEndYmd, String adnm, String adCn, Long adAmt, String useYn, String rgtr, LocalDateTime regDt, String delYn, User user) {
        return new AdMng().builder()
                .adSn(comtecopseqService.generateUUID_AD()).adSttscd(adSttscd)
                .adBgngYmd(adBgngYmd).adEndYmd(adEndYmd) // 광고 시작 ~ 종료 일자
                .aplctBgngYmd(aplctBgngYmd).aplctEndYmd(aplctEndYmd) // 응모 시작 ~ 응모 종료 일자
                .adnm(adnm).adCn(adCn).adAmt(adAmt).useYn(useYn).regDt(regDt).delYn(delYn).user(user)
                .build();
    }

    /**
     * 응모자 빌더
     *
     * @param adMng
     * @param user
     * @param rappYn
     * @param rappPnlKm
     * @param aplctRegDt
     * @return
     */
    private AplctUserMng aplctUserMngBuilder(AdMng adMng, User user, AdPrgrsSttsCd adPrgrsSttscd, String rappYn,
                                            String rappLeftAtchfileSn, String rappRightAtchfileSn,
                                            String rappBackAtchfileSn, String rappPnlAtchfileSn, Integer rappPnlKm,
                                            LocalDateTime aplctRegDt) {
        return new AplctUserMng().builder()
                .aplctSn(comtecopseqService.generateUUID_APL())
                .adMng(adMng)
                .user(user)
                .adPrgrsSttscd(adPrgrsSttscd)
                .rappYn(rappYn)
                .rappLeftAtchfileSn(rappLeftAtchfileSn)
                .rappRightAtchfileSn(rappRightAtchfileSn)
                .rappBackAtchfileSn(rappBackAtchfileSn)
                .rappPnlAtchfileSn(rappPnlAtchfileSn)
                .rappPnlKm(rappPnlKm)
                .aplctRegDt(aplctRegDt)
                .build();

    }



    private String createRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}
