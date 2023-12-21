package gw.apiserver.oms.cfm.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class CommonFileUtil {
    // 파일 허용 확장자
    private static final String FILE_ALLOW_EXTENSION = "|gul|xls|xlsx|doc|docx|hwp|pdf|ppt|pptx|jpg|gif|bmp|jpeg|psd|pdf|png|zip|txt|";

    /**
     * 디스크에 파일 저장
     * @param fileStrePath String 파일 저장 경로
     * @param fileStreNm String 파일 저장명
     * @param file MultipartFile 파일
     */
    public static void saveFile(String fileStrePath, String fileStreNm, MultipartFile file) {
        try {
            String uploadPath = fileStrePath + fileStreNm;
            createDir(fileStrePath);
            file.transferTo(new File(uploadPath));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 파일 디렉토리 생성
     * @param filePath String : 파일 경로
     */
    public static void createDir(String filePath) {
        File filePathDir = new File(filePath);
        if (!filePathDir.exists()) {
            filePathDir.setExecutable(false, true);
            filePathDir.setReadable(true);
            filePathDir.setWritable(false, true);
            filePathDir.mkdirs();
        }
    }

    /**
     * 파일 삭제
     *
     * @param filePath String
     * @return int
     */
    public static int deleteFile(String filePath) {
        File file = new File(filePath);
        int ret = 0;
        if (file.exists()) {
            deleteFileSync(file);
            ret++;
        } else {
            log.debug(" file delete false");
        }

        return ret;
    }

    /**
     * 파일 삭제 실제 수행
     * @param file File
     */
    private static synchronized void deleteFileSync(File file) {
        file.delete();
    }

    /**
     * 파일 확장자 체크
     * @param fileExtension String
     * @return boolean
     */
    public static boolean checkFileExtension(String fileExtension) {
        return FILE_ALLOW_EXTENSION.contains("|" + fileExtension + "|");
    }

}
