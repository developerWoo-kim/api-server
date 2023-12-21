package gw.apiserver.ad.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gw.apiserver.common.security.api.service.JwtApiService;
import gw.apiserver.common.security.core.JwtTokenProvider;
import gw.apiserver.common.security.core.response.dto.AccessTokenDto;
import gw.apiserver.oms.aplct.controller.form.AdProofForm;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AplctprgrsControllerTest {
    @Autowired ObjectMapper mapper;
    @Autowired MockMvc mvc;
    @Autowired JwtTokenProvider tokenProvider;
    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("광고 증빙 API 테스트")
    void adProofApiTest() throws Exception {
        //given
        User user = userRepository.findById("USR_00002345").orElseThrow();
        AccessTokenDto accessTokenDto = tokenProvider.generateAccessTokenDto(user.getUserId(), "NOTING");

        String aplctprgrsSn = "APR_0000001472";
        int pnlKm = 2031;
        //when
        MockMultipartFile evdncLeftAtchfile = createMultipartFile("evdncLeftAtchfile", "/Users/gimgeon-u/Desktop/danggeon.png");

        //then
        mvc.perform(
                multipart(HttpMethod.PUT,"/api/v1/aplct/proof")
                        .file(evdncLeftAtchfile)
                        .file(createMultipartFile("evdncRightAtchfile","/Users/gimgeon-u/Desktop/danggeon.png"))
                        .file(createMultipartFile("evdncBackAtchfile", "/Users/gimgeon-u/Desktop/danggeon.png"))
                        .file(createMultipartFile("evdncPnlAtchfile","/Users/gimgeon-u/Desktop/danggeon.png"))
                        .param("aplctprgrsSn", aplctprgrsSn)
                        .param("pnlKm", String.valueOf(pnlKm))
                        .header("Authorization", accessTokenDto.getGrantType() + " " + accessTokenDto.getAccessToken())
        ).andExpect(status().isOk());
    }

    private MockMultipartFile createMultipartFile(String name, String filePath) throws IOException {
        File file = new File(filePath);
        return new MockMultipartFile(
                name,
                file.getName(),
                Files.probeContentType(file.toPath()),
                new FileInputStream(filePath)
        );
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

    //    @Test
//    @DisplayName("광고 증빙 API 테스트")
//    void adProofApiTest() throws Exception {
//        //given
//        String aplctprgrsSn = "APR_0000001472";
//        MultipartFile leftFile = fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png");
//        MultipartFile rightFile = fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png");
//        MultipartFile backFile = fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png");
//        MultipartFile pnlFile = fileToMultipartFile("/Users/gimgeon-u/Desktop/danggeon.png");
//        int pnlKm = 2031;
//
//        //when
//        String body = mapper.writeValueAsString(
//                AdProofForm.builder()
//                        .aplctprgrsSn(aplctprgrsSn)
//                        .evdncLeftAtchfile(leftFile)
//                        .evdncRightAtchfile(rightFile)
//                        .evdncBackAtchfile(backFile)
//                        .evdncPnlAtchfile(pnlFile)
//                        .pnlKm(pnlKm)
//                        .build()
//        );
//        //then
//        mvc.perform(post("/api/v1/aplct/proof")
//                        .content(body) //HTTP Body에 데이터를 담는다
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED) //보내는 데이터의 타입을 명시
//                )
//                .andExpect(status().isOk());
//    }
}
