package gw.apiserver.oms.cfm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comtnfile")
public class CommonFileMaster {

    @Id
    @Column(name = "atch_file_id")
    private String atchFileId;

    @JsonIgnore
    @OneToMany(mappedBy = "commonFileMaster", cascade = CascadeType.ALL)
    private List<CommonFileDetail> fileDetailList = new ArrayList<>();

    private LocalDateTime creatDt;
    private String useAt;

    public void addCommonFileDetail(CommonFileDetail commonFileDetail) {
        this.fileDetailList.add(commonFileDetail);
        commonFileDetail.setCommonFileMaster(this);
    }

    public static CommonFileMaster createCommonMasterFile(String atchFileId, CommonFileDetail... commonFileDetail) {
        CommonFileMaster commonFileMaster = new CommonFileMaster();
        commonFileMaster.setAtchFileId(atchFileId);

        for (CommonFileDetail fileDetail : commonFileDetail) {
            commonFileMaster.addCommonFileDetail(fileDetail);
        }
        commonFileMaster.setCreatDt(LocalDateTime.now());
        commonFileMaster.setUseAt("Y");

        return commonFileMaster;
    }
}
