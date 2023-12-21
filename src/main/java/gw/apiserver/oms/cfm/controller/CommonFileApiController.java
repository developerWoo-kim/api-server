package gw.apiserver.oms.cfm.controller;

import gw.apiserver.oms.cfm.service.CommonFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CommonFileApiController {
    private final CommonFileService commonFileService;

    @PostMapping("/api/v1/file/test")
    public void fileUploadTest(MultipartFile file) {
        commonFileService.save("adProof", file);
    }

    @PutMapping("/api/v1/file/test/{atchFileId}")
    public void updateTest(@PathVariable("atchFileId") String atchFileId, MultipartFile file) {
        commonFileService.update(atchFileId, "user", file);
    }

    @DeleteMapping("/api/v1/file/test/{fileSn}")
    public void fileUploadTest(@PathVariable("fileSn") String fileSn) {
        commonFileService.deleteSingleFile(fileSn);
    }
}
