package gw.apiserver.common.utils.reponse.meta;

import gw.apiserver.common.utils.reponse.error.CommonErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonResponse extends AbstractCommonResponse {

    public CommonResponse(String status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

    public static CommonResponse createResponse(String status, String message) {
        return new CommonResponse(status, message);
    };
}
