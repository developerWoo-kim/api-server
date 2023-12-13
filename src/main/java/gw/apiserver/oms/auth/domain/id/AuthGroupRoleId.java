package gw.apiserver.oms.auth.domain.id;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class AuthGroupRoleId implements Serializable {

    @Column(name = "authrt_group_sn")
    private String authrtGroupSn;
    private String authrtRole;

    @Builder
    public AuthGroupRoleId(String authrtGroupSn, String authrtRole) {
        this.authrtGroupSn = authrtGroupSn;
        this.authrtRole = authrtRole;
    }
}
