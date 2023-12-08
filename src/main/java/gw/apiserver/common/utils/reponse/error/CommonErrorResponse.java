package gw.apiserver.common.utils.reponse.error;


import gw.apiserver.common.utils.reponse.meta.AbstractCommonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 공통 API ERROR RESPONSE 포맷
 *
 * @author gwkim
 * @since 2023.08.31
 * @version 1.0
 */
@Schema(description = "에러 응답")
@Getter @Setter
@Builder
public class CommonErrorResponse {
    private String code;
    private String message;
    private String path;

    public CommonErrorResponse(String code, String message, String requestUrl) {
        this.setCode(code);
        this.setMessage(message);
        this.setPath(requestUrl);
    }

    public static CommonErrorResponse commonError(String errorCode, String message, String requestUrl) {
        return new CommonErrorResponse(errorCode, message, requestUrl);
    };
}
