package gw.apiserver.oms.auth.domain;

import gw.apiserver.oms.common.api.domain.ApiMng;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_authrt_group_api")
public class AuthGroupApi {

    @Id
    @Column(name = "authrt_group_api_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authrtGroupApiId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_id")
    private ApiMng apiMng;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authrt_group_sn")
    private AuthGroup authGroup;
}
