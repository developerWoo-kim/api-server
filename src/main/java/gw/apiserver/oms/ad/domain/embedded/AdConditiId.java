package gw.apiserver.oms.ad.domain.embedded;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 광고 응모 조건 비식별 복합키 매핑을 위한 클래스
 *
 * @create gwkim
 * @since 2023.08.03
 * @version 0.0.1
 */
@Embeddable
@NoArgsConstructor
public class AdConditiId implements Serializable {
    @Column(name = "ad_sn")
    private String adSn;

    @Column(name = "sort")
    private int sort;

    public AdConditiId(String adSn, int sort) {
        this.adSn = adSn;
        this.sort = sort;
    }
}
