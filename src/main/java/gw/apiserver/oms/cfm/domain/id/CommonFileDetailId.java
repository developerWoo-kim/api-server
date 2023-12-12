package gw.apiserver.oms.cfm.domain.id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class CommonFileDetailId implements Serializable {
    @Column(name = "file_sn")
    private String fileSn;
    private String atchFileId;

    @Builder
    public CommonFileDetailId(String fileSn, String atchFileId) {
        this.fileSn = fileSn;
        this.atchFileId = atchFileId;
    }
}
