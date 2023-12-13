package gw.apiserver.oms.auth.domain;

import gw.apiserver.oms.auth.domain.id.AuthGroupRoleId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_authrt_group_role")
public class AuthGroupRole {
    @EmbeddedId
    private AuthGroupRoleId id;

    @MapsId("authrtGroupSn")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authrt_group_sn")
    private AuthGroup authGroup;
}
