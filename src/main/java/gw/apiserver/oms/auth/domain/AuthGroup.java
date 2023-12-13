package gw.apiserver.oms.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_authrt_group")
public class AuthGroup {

    @Id
    @Column(name = "authrt_group_sn")
    private String authrtGroupSn;
    private String authrtGroupNm;
    private String authrtGroupDtl;
    private LocalDateTime regDt;

    @OneToMany(mappedBy = "authGroup", cascade = CascadeType.ALL)
    private List<AuthGroupRole> roleList = new ArrayList<>();
}
