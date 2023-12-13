package gw.apiserver.oms.auth.domain;

import gw.apiserver.oms.auth.domain.id.AuthGroupUserId;
import gw.apiserver.oms.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_authrt_group_user")
public class AuthGroupUser {

    @EmbeddedId
    private AuthGroupUserId id;

    @MapsId("userSn")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sn")
    private User user;

    @MapsId("authrtGroupSn")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authrt_group_sn")
    private AuthGroup authGroup;
}
