package gw.apiserver.oms.user.service;

import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<CommonResponse> idDuplicationCheck(String id);
}
