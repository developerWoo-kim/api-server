package gw.apiserver.common.utils.reponse.meta;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommonResponse {
    private String status;
    private String message;
    public CommonResponse(String status, String message) {
        this.setStatus(status);
        this.setMessage(message);
    }

    public static CommonResponse createResponse(String status, String message) {
        return new CommonResponse(status, message);
    };
}
