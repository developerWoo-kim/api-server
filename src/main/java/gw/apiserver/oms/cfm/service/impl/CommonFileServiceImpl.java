package gw.apiserver.oms.cfm.service.impl;

import gw.apiserver.common.utils.CommonGlobalsConfigValue;
import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import gw.apiserver.oms.cfm.domain.CommonFileDetail;
import gw.apiserver.oms.cfm.domain.CommonFileMaster;
import gw.apiserver.oms.cfm.repository.CommonFileDetailRepository;
import gw.apiserver.oms.cfm.repository.CommonFileMasterRepository;
import gw.apiserver.oms.cfm.service.CommonFileService;
import gw.apiserver.oms.cfm.util.CommonFileUtil;
import gw.apiserver.oms.common.cmmseq.service.ComtecopseqService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 공통 파일 서비스 구현체
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CommonFileServiceImpl implements CommonFileService {
    private final CommonGlobalsConfigValue commonGlobalsConfigValue;
    private final ComtecopseqService comtecopseqService;
    private final CommonFileMasterRepository commonFileMasterRepository;
    private final CommonFileDetailRepository commonFileDetailRepository;

    /**
     * 파일 저장
     * @param category String 파일 카테고리
     * @param file MultipartFile... 파일 가변 인자
     */
    @Override
    public void save(String category, MultipartFile... file) {
        List<CommonFileDetail> commonFileDetailList = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            // 상세 파일 생성
            CommonFileDetail commonFile = CommonFileDetail.createCommonFileDetail(
                            comtecopseqService.generateUUID_FLED(),
                            commonGlobalsConfigValue.getFileStorePath(),
                            category,
                            multipartFile
                    );

            // 파일 확장자 체크
            if(!CommonFileUtil.checkFileExtension(commonFile.getFileExtsn())) {
                throw new GlobalApiException(CommonError.CFM_FILE_NOT_ALLOWED);
            }
            // 파일 저장 (to Disk)
            CommonFileUtil.saveFile(commonFile.getFileStreCours(), commonFile.getStreFileNm(), multipartFile);
            commonFileDetailList.add(commonFile);
        }

        // 마스터 파일 엔티티 생성
        CommonFileMaster commonMasterFile = CommonFileMaster.createCommonMasterFile(
                comtecopseqService.generateUUID_FLE(),
                commonFileDetailList.toArray(new CommonFileDetail[0])
        );

        commonFileMasterRepository.save(commonMasterFile);
    }

    @Override
    public void update(String atchFileId, String category, MultipartFile... file) {
        CommonFileMaster fileMaster = commonFileMasterRepository.findById(atchFileId).orElseThrow();

        for (MultipartFile multipartFile : file) {
            // 상세 파일 생성
            CommonFileDetail commonFile = CommonFileDetail.createCommonFileDetail(
                    comtecopseqService.generateUUID_FLED(),
                    commonGlobalsConfigValue.getFileStorePath(),
                    category,
                    multipartFile
            );

            commonFile.setCommonFileMaster(fileMaster);

            // 파일 확장자 체크
            if(!CommonFileUtil.checkFileExtension(commonFile.getFileExtsn())) {
                throw new GlobalApiException(CommonError.CFM_FILE_NOT_ALLOWED);
            }
            // 파일 저장 (to Disk)
            CommonFileUtil.saveFile(commonFile.getFileStreCours(), commonFile.getStreFileNm(), multipartFile);

            commonFileDetailRepository.save(commonFile);
        }
    }

    @Override
    public void deleteSubFileOfMasterFile(String atchFileId) {
        CommonFileMaster fileMaster = commonFileMasterRepository.findById(atchFileId).orElseThrow();

        if(!fileMaster.getFileDetailList().isEmpty()) {
            for (CommonFileDetail fileDetail : fileMaster.getFileDetailList()) {
                String uploadPath = fileDetail.getFileStreCours() + fileDetail.getStreFileNm();
                CommonFileUtil.deleteFile(uploadPath);
                commonFileDetailRepository.delete(fileDetail);
            }
        }
    }

    @Override
    public void deleteSingleFile(String fileSn) {
        CommonFileDetail fileDetail = commonFileDetailRepository.findById(fileSn)
                .orElseThrow(() -> new GlobalApiException(CommonError.CFM_FILE_NOT_FOUND));
        String uploadPath = fileDetail.getFileStreCours() + fileDetail.getStreFileNm();
        CommonFileUtil.deleteFile(uploadPath);
        commonFileDetailRepository.delete(fileDetail);
    }
}
