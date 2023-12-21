package gw.apiserver.ad.service;

import gw.apiserver.dummy.DummyGenerator;
import gw.apiserver.oms.ad.domain.AdMng;
import gw.apiserver.oms.ad.domain.AdSttsCd;
import gw.apiserver.oms.aplct.domain.AdPrgrsSttsCd;
import gw.apiserver.oms.aplct.domain.AplctUserMng;
import gw.apiserver.oms.aplct.domain.Aplctprgrs;
import gw.apiserver.oms.aplct.repository.AplctprgrsRepository;
import gw.apiserver.oms.cfm.service.CommonFileService;
import gw.apiserver.oms.user.domain.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AplctprgrsServiceTest {
    @Autowired AplctprgrsRepository aplctprgrsRepository;
    @Autowired CommonFileService commonFileService;
    @Autowired DummyGenerator dummyGenerator;

    @Test
    @DisplayName("증빙 테스트")
    void adProofTest() throws IOException {
        User adUser = dummyGenerator.createAdUser();

        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 2, 29);
        LocalDate aplctStart = LocalDate.of(2023, 11, 15);
        LocalDate aplctEnd = LocalDate.of(2024, 11, 20);
        AdMng ad = dummyGenerator.createAd(adUser, AdSttsCd.MNG003002, startDate, endDate, aplctStart, aplctEnd);

        AplctUserMng aplctUser = dummyGenerator.createAplctUser(ad, AdPrgrsSttsCd.MNG004003, 0, LocalDateTime.now());
        dummyGenerator.createAplctprgrs(ad, aplctUser);

        List<Aplctprgrs> aplctprgrsList = aplctprgrsRepository.findByAplctUserMng_AplctSn(aplctUser.getAplctSn()).orElseThrow();
        Aplctprgrs aplctprgrs1 = aplctprgrsList.get(0);

        String leftSn = commonFileService.save("ad_proof", fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png"));
        String rightSn = commonFileService.save("ad_proof", fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png"));
        String backSn = commonFileService.save("ad_proof", fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png"));
        String pnlSn = commonFileService.save("ad_proof", fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png"));


        aplctprgrs1.setEvdncLeftAtchfileSn(leftSn);
        aplctprgrs1.setEvdncRightAtchfileSn(rightSn);
        aplctprgrs1.setEvdncBackAtchfileSn(backSn);
        aplctprgrs1.setEvdncPnlAtchfileSn(pnlSn);
        aplctprgrs1.setDriveKm(3504);
        aplctprgrs1.setEvdncYn("Y");
    }

    @Test
    @DisplayName("로컬 파일 테스트")
    void localFileTest() throws IOException {
        MultipartFile multipartFile = fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png");
        Assertions.assertThat(multipartFile.getOriginalFilename()).isEqualTo("danggeon.png");
    }



    MultipartFile fileToMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileItem fileItem = new DiskFileItem("mainFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length(), file.getParentFile());
        try {
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
        } catch (IOException ex) {
        }
        return new CommonsMultipartFile(fileItem);
    }
}
