package gw.apiserver.common.utils.reponse.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;


/**
 * 공통 에러 코드 & 메세지
 *
 * @author kimgunwoo
 * @since 2023.11.21
 * @version 1.0v
 */
@Getter
public enum CommonError {
    CMM_SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"system-001","알수 없는 에러가 발생 하였습니다.\n관리자에게 문의해 주시기 바랍니다."),
    CMM_INVALID_PARAMETER(HttpStatus.BAD_REQUEST,"system-002","유효하지 않은 데이터가 포함되어있습니다."),
    //== 파일 관련 에러 코드 ==//
    CFM_FILE_NOT_ALLOWED(HttpStatus.BAD_REQUEST,"file-001","허용되지 않은 파일이 존재합니다."),
    CFM_FILE_NOT_FOUND(HttpStatus.NOT_FOUND,"file-002","파일이 존재하지 않습니다."),
    //== 로그인 및 권한 관련 에러 코드 ==//
    CMM_AUTH_BAD_CREDENTIALS(HttpStatus.BAD_REQUEST,"auth-001","아이디 또는 비밀번호를 잘못 입력하셨습니다.\n입력하신 내용을 다시 확인해주세요."),
    CMM_AUTH_ACCOUNT_EXPIRED(HttpStatus.NOT_ACCEPTABLE, "auth-002", "계정만료"),
    CMM_AUTH_CREDENTIALS_EXPIRED(HttpStatus.NOT_ACCEPTABLE,"auth-003", "비밀번호 변경주기가 도래하였습니다."),
    CMM_AUTH_ACCOUNT_DISABLED(HttpStatus.NOT_ACCEPTABLE,"auth-004", "계정비활성화"),
    CMM_AUTH_ACCOUNT_LOCKED(HttpStatus.NOT_ACCEPTABLE,"auth-005", ""),
    CMM_AUTH_REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "auth-006", "Refresh Token이 누락 되었습니다."),
    CMM_AUTH_ACCESS_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "auth-007", "Access Token이 누락 되었습니다."),
    CMM_AUTH_ROLE_NOT_FOUND(HttpStatus.FORBIDDEN, "auth-008", "권한이 존재하지 않습니다."),

    //== 회원 관련 에러 코드 ==//
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user-001", "회원정보가 존재하지 않습니다."),

    //== 광고 관련 에러 코드 ==//
    AD_APPLY_DUPLICATION(HttpStatus.BAD_REQUEST, "ad-001", "이미 응모한 광고입니다."),
    AD_ALREADY_EXISTS_RUNNING(HttpStatus.BAD_REQUEST, "ad-002", "진행중인 광고가 존재합니다."),
    AD_NO_APPLY_PERIOD(HttpStatus.BAD_REQUEST, "ad-003", "응모 기간이 아닙니다."),
    AD_NOT_MATCH_CONDITION(HttpStatus.BAD_REQUEST, "ad-004", "응모 조건에 해당되지 않습니다.");

    private HttpStatus status;
    private String code;
    private String message;

    CommonError(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
