package gw.apiserver.ad;

import gw.apiserver.oms.aplct.repository.AplctUserMngRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AplctUserMngRepositoryTest {
    @Autowired
    AplctUserMngRepository aplctUserMngRepository;

    @Test
    @DisplayName("응모 중복 체크 테스트")
    public void dupleTest() {
        long l = aplctUserMngRepository.countAplctUserMngByAdMng_AdSnAndUser_UserSn("AD_0000012", "USR_00000006");
        System.out.println(l);
    }

}
