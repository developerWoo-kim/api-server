package gw.apiserver.oms.common.cmmseq.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 시퀀스 테이블
 *
 * @create gwkim
 * @since 2023.08.01
 * @version 0.0.1
 */
@Getter @Setter
@NoArgsConstructor
@Entity(name = "comtecopseq")
public class Comtecopseq {

    @Id
    @Column(name = "table_name")
    private String tableName;
    private long nextId;

    //== 편의 메소드 ==//
    /**
     * 증감 연산 ( 변경 감지 )
     */
    public void incrementId() {
        this.nextId += 1;
    }
}
