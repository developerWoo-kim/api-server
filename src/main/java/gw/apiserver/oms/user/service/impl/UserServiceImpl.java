package gw.apiserver.oms.user.service.impl;

import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import gw.apiserver.oms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public ResponseEntity<CommonResponse> idDuplicationCheck(String id) {
        try {
            Optional<User> findUser = userRepository.findById(id);

            HttpStatus status;
            CommonResponse response;
            if (findUser.isPresent()) {
                status = HttpStatus.BAD_REQUEST;
                response = CommonResponse.createResponse(status.toString(), "이미 존재하는 아이디 입니다.");
                log.warn("Duplicate user ID detected: {}", id);
            } else {
                status = HttpStatus.OK;
                response = CommonResponse.createResponse(status.toString(), "사용 가능한 아이디 입니다.");
            }

            return ResponseEntity.status(status).body(response);
        } catch (Exception e) {
            log.error("Error while checking user ID duplication for ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.createResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "서버 오류"));
        }
    }
}
