package gw.apiserver.oms.user.service;

import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.user.controller.form.UserFileInsertForm;
import gw.apiserver.oms.user.controller.form.UserJoinForm;
import gw.apiserver.oms.user.controller.form.UserUpdateForm;
import org.springframework.http.ResponseEntity;

public interface UserService {
    /**
     * 아이디 중복 체크
     * @param id String
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> idDuplicationCheck(String id);

    /**
     * 회원가입
     * @param form UserJoinForm
     * @return ResponseEntity<CommonResponse>
     */
    ResponseEntity<CommonResponse> joinUser(UserJoinForm form);

    /**
     * 회원정보 수정
     * @param form UserUpdateForm
     */
    void updateUser(UserUpdateForm form);

    /**
     * 회원관련 파일 업데이트
     * @param form UserFileInsertForm
     */
    void updateUserFile(UserFileInsertForm form);
}
