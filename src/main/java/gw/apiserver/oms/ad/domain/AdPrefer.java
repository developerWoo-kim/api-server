package gw.apiserver.oms.ad.domain;

import gw.apiserver.oms.ad.domain.embedded.AdPreferId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity(name = "op_tb_ad_prefer")
public class AdPrefer {

    @EmbeddedId
    private AdPreferId id;

    private int sort;

}
