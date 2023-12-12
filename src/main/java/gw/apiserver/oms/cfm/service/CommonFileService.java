package gw.apiserver.oms.cfm.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface CommonFileService {

    /**
     * 파일 저장
     * @param category String 카일 카테고리
     * @param file MultipartFile... 파일 가변
     * @return String 마스터 파일 아이디
     */
    String save(String category, MultipartFile file);

    /**
     * 파일 업데이트
     * @param atchFileId String 마스터파일 아이디
     * @param category String 카테고리
     * @param file MultipartFile 파일 가변
     */
    void update(String atchFileId, String category, MultipartFile... file);

    /**
     * 마스터 파일의 하위 파일 모두 삭제
     * @param atchFileId String
     */
    void deleteSubFileOfMasterFile(String atchFileId);

    /**
     * 단일 파일 삭제
     * @param fileSn String
     */
    void deleteSingleFile(String fileSn);
}
