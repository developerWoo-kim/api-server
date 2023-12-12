package gw.apiserver.oms.cfm.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "comtnfiledetail")
public class CommonFileDetail implements Comparable<CommonFileDetail>{

    @Id
    @Column(name = "file_sn")
    private String fileSn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atch_file_id")
    private CommonFileMaster commonFileMaster;

    private String fileStreCours;
    private String streFileNm;
    private String orignlFileNm;
    private String fileExtsn;

    @Lob
    private String fileCn;
    private String fileSize;
    private String useAt;

    @Builder
    public CommonFileDetail(String fileSn, String fileStreCours, String streFileNm, String orignlFileNm, String fileExtsn, String fileCn, String fileSize, String useAt) {
        this.fileSn = fileSn;
        this.fileStreCours = fileStreCours;
        this.streFileNm = streFileNm;
        this.orignlFileNm = orignlFileNm;
        this.fileExtsn = fileExtsn;
        this.fileCn = fileCn;
        this.fileSize = fileSize;
        this.useAt = useAt;
    }

    public static CommonFileDetail createCommonFileDetail(String fileSn, String uploadPath, String category, MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        String saveFileName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + fileExtension;
        String fileSize = String.valueOf(file.getSize());

        LocalDate nowDate = LocalDate.now();
        int year = nowDate.getYear();
        int month = nowDate.getMonthValue();

        String path = uploadPath + category + "/" + year + "/" + month + "/";

        return CommonFileDetail.builder()
                .fileSn(fileSn)
                .fileStreCours(path)
                .streFileNm(saveFileName)
                .orignlFileNm(originalFileName)
                .fileExtsn(fileExtension)
                .fileCn("0")
                .fileSize(fileSize)
                .useAt("Y")
                .build();
    }


    @Override
    public int compareTo(CommonFileDetail o) {
        if(this == o) return 0;
        return this.getFileSn().compareTo(o.getFileSn());
    }
}
