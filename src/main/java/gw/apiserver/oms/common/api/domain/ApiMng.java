package gw.apiserver.oms.common.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gw.apiserver.oms.auth.domain.AuthGroupApi;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "op_tb_api")
public class ApiMng {

    @Id
    @Column(name = "api_id")
    private String apiId;
    private String apiNm;

    @JsonIgnore
    @OneToMany(mappedBy = "apiMng")
    private List<AuthGroupApi> authGroupApiList = new ArrayList<>();

    private String domainNm;
    private String pathParamUseAt;
    private String reqType;
    private String reqUri;
    private LocalDate regDt;
    private LocalDate updDt;

    //== 편의메서드 ==//
    public boolean uriMatcher(String requestUri, String requestType) {
        String targetUri = pathParamUseAt.equals("Y") ? requestUri.substring(0, requestUri.lastIndexOf("/") + 1) : requestUri;
        String compareUri = pathParamUseAt.equals("Y") ? this.reqUri.substring(0, this.reqUri.lastIndexOf("/") + 1) : this.reqUri;

        if(targetUri.equals(compareUri) && requestType.equals(this.reqType)) {
            return true;
        }

        return false;
    }
}
