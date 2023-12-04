package gw.apiserver.oms.common.cmmcode.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity(name = "comtccmmndetailcode")
public class ComtCcmnDetailCode {
    @Id
    @Column(name = "code")
    private String code;
    private String codeId;
    private String codeNm;
    private String codeDc;
    private String useAt;
    private LocalDateTime frstRegistPnttm;
    private LocalDateTime frstRegisterId;
    private LocalDateTime lastUpdtPnttm;
    private LocalDateTime lastUpduserId;
    private int sort;
}
