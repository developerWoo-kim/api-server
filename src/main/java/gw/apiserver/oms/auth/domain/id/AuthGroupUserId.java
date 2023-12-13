package gw.apiserver.oms.auth.domain.id;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
public class AuthGroupUserId implements Serializable {
    private String userSn;
    private String authrtGroupSn;

    @Builder
    public AuthGroupUserId(String userSn, String authrtGroupSn) {
        this.userSn = userSn;
        this.authrtGroupSn = authrtGroupSn;
    }
}
