package gw.apiserver.oms.ad.domain.embedded;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
public class AdPreferId implements Serializable {
    @Column(name = "ad_sn")
    private String adSn;

    @Column(name = "pref_condinm")
    private String prefCondinm;

    public AdPreferId(String adSn, String prefCondinm) {
        this.adSn = adSn;
        this.prefCondinm = prefCondinm;
    }
}
