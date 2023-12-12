package gw.apiserver.user;

import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.common.cmmcode.domain.MainDrivergnCd;
import gw.apiserver.oms.common.cmmcode.domain.VhclLoadweightCd;
import gw.apiserver.oms.common.cmmcode.domain.VhclTypeCd;
import gw.apiserver.oms.user.controller.form.UserJoinForm;
import gw.apiserver.oms.user.controller.form.UserUpdateForm;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import gw.apiserver.oms.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    public void joinUserTest() {
        String userId = "apiTest001";

        UserJoinForm form = new UserJoinForm();
        form.setUserNm("김건우");
        form.setUserId(userId);
        form.setPswd("qwer1234!");
        form.setBzmnNm("사업자");
        form.setBzmnNo("12312341234");
        form.setVhclNo("346거1521");
        form.setVhclLoadweightCd(VhclLoadweightCd.MNG001002);
        form.setVhclTypeCd(VhclTypeCd.MNG002002);
        form.setMainDrivergnCd("USR002001, USR002002");

        ResponseEntity<CommonResponse> resp = userService.joinUser(form);

        Assertions.assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("회원정보 수정 테스트")
    public void updateUserTest() {
        UserUpdateForm form = new UserUpdateForm();
        form.setUserSn("USR_00002311");
        form.setPswd("1234");

        userService.updateUser(form);
    }
}
