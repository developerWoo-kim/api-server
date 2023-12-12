package gw.apiserver.oms.user.service.impl;

import gw.apiserver.common.utils.reponse.code.CommonError;
import gw.apiserver.common.utils.reponse.exception.GlobalApiException;
import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import gw.apiserver.oms.cfm.service.CommonFileService;
import gw.apiserver.oms.common.cmmseq.service.ComtecopseqService;
import gw.apiserver.oms.user.controller.form.UserFileInsertForm;
import gw.apiserver.oms.user.controller.form.UserJoinForm;
import gw.apiserver.oms.user.controller.form.UserUpdateForm;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import gw.apiserver.oms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ComtecopseqService comtecopseqService;
    private final PasswordEncoder passwordEncoder;

    private final CommonFileService commonFileService;

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

    @Override
    @Transactional
    public ResponseEntity<CommonResponse> joinUser(UserJoinForm form) {
        try {
            form.setPswd(passwordEncoder.encode(form.getPswd())); // 비밀번호 암호화

            User user = User.createUser(form, comtecopseqService.generateUUID_USR());

            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error during join user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CommonResponse.createResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Error during join user"));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.createResponse(HttpStatus.OK.toString(), "회원가입이 완료되었습니다."));
    }

    @Override
    @Transactional
    public void updateUser(UserUpdateForm form) {
        User user = userRepository.findById(form.getUserSn()).orElseThrow(() ->
                new GlobalApiException(CommonError.USER_NOT_FOUND));

        user.setPswd(passwordEncoder.encode(form.getPswd()));
        user.setBzmnNm(form.getBzmnNm());
        user.setRprsvNm(form.getRprsvNm());
        user.setTelno(form.getTelno());
        user.setZip(form.getZip());
        user.setAddr(form.getAddr());
        user.setDaddr(form.getDaddr());
        user.setDpstrNm(form.getDpstrNm());
        user.setMainDrivergnCd(User.conversionMainDriverCd(form.getMainDrivergnCd()));

    }

    @Override
    @Transactional
    public void updateUserFile(UserFileInsertForm form) {
        User user = userRepository.findById(form.getUserSn()).orElseThrow();
        // 차량 좌측 사진
        if(form.getLeftImg() != null && !form.getLeftImg().isEmpty()) {
            if(user.getLeftAtchfileSn() != null) {
                commonFileService.deleteSubFileOfMasterFile(user.getLeftAtchfileSn());
                commonFileService.update(user.getLeftAtchfileSn(), "user", form.getLeftImg());
            } else {
                String atchFileId = commonFileService.save("user", form.getLeftImg());
                user.setLeftAtchfileSn(atchFileId);
            }
        }

        // 차량 우측 사진
        if(form.getRightImg() != null && !form.getRightImg().isEmpty()) {
            if(user.getRightAtchfileSn() != null) {
                commonFileService.deleteSubFileOfMasterFile(user.getRightAtchfileSn());
                commonFileService.update(user.getRightAtchfileSn(), "user", form.getRightImg());
            } else {
                String atchFileId = commonFileService.save("user", form.getRightImg());
                user.setRightAtchfileSn(atchFileId);
            }
        }

        // 차량 후방 사진
        if(form.getBackImg() != null && !form.getBackImg().isEmpty()) {
            if(user.getBackAtchfileSn() != null) {
                commonFileService.deleteSubFileOfMasterFile(user.getBackAtchfileSn());
                commonFileService.update(user.getBackAtchfileSn(), "user", form.getBackImg());
            } else {
                String atchFileId = commonFileService.save("user", form.getBackImg());
                user.setBackAtchfileSn(atchFileId);
            }
        }

        // 계기판 사진
        if(form.getDashBoardImg() != null && !form.getDashBoardImg().isEmpty()) {
            if(user.getPnlAtchfileSn() != null) {
                commonFileService.deleteSubFileOfMasterFile(user.getPnlAtchfileSn());
                commonFileService.update(user.getPnlAtchfileSn(), "user", form.getDashBoardImg());
            } else {
                String atchFileId = commonFileService.save("user", form.getDashBoardImg());
                user.setPnlAtchfileSn(atchFileId);
            }
        }

        // 차량 등록증 사진
        if(form.getCrcImg() != null && !form.getCrcImg().isEmpty()) {
            if(user.getVhclRgstrAtchfileSn() != null) {
                commonFileService.deleteSubFileOfMasterFile(user.getVhclRgstrAtchfileSn());
                commonFileService.update(user.getVhclRgstrAtchfileSn(), "user", form.getCrcImg());
            } else {
                String atchFileId = commonFileService.save("user", form.getCrcImg());
                user.setVhclRgstrAtchfileSn(atchFileId);
            }
        }

        // 신분증 사진
        if(form.getIdCardImg() != null && !form.getIdCardImg().isEmpty()) {
            if(user.getIdcardAtchfileSn() != null) {
                commonFileService.deleteSubFileOfMasterFile(user.getIdcardAtchfileSn());
                commonFileService.update(user.getIdcardAtchfileSn(), "user", form.getIdCardImg());
            } else {
                String atchFileId = commonFileService.save("user", form.getIdCardImg());
                user.setIdcardAtchfileSn(atchFileId);
            }
        }


    }
}
