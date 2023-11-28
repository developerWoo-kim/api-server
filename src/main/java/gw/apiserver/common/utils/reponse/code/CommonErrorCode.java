package gw.apiserver.common.utils.reponse.code;

import lombok.Getter;


/**
 * 공통 에러 코드 & 메세지
 *
 * @author kimgunwoo
 * @since 2023.11.21
 * @version 1.0v
 */
@Getter
public enum CommonErrorCode {
    CMM_SYSTEM_ERROR("system-001", "알수 없는 에러가 발생 하였습니다.\n관리자에게 문의해 주시기 바랍니다."),
    //== 로그인 관련 에러 코드 ==//
    CMM_AUTH_BAD_CREDENTIALS("auth-001", "아이디 또는 비밀번호를 잘못 입력하셨습니다.\n입력하신 내용을 다시 확인해주세요."),
    CMM_AUTH_ACCOUNT_EXPIRED("auth-002", "계정만료"),
    CMM_AUTH_CREDENTIALS_EXPIRED("auth-003", "비밀번호 변경주기가 도래하였습니다."),
    CMM_AUTH_ACCOUNT_DISABLED("auth-004", "계정비활성화"),
    CMM_AUTH_ACCOUNT_LOCKED("auth-005", ""),
    CMM_AUTH_REFRESH_TOKEN_NOT_FOUND("auth-006", "Refresh Token이 누락 되었습니다."),
    CMM_AUTH_ACCESS_TOKEN_NOT_FOUND("auth-007", "Access Token이 누락 되었습니다.");


    private String code;
    private String message;

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
