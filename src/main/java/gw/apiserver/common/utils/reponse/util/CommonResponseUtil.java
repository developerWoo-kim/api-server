package gw.apiserver.common.utils.reponse.util;

import gw.apiserver.common.utils.reponse.meta.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonResponseUtil {
    public static ResponseEntity<CommonResponse> responseInternal(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(makeResponse(status.toString(), message));
    }

    private static CommonResponse makeResponse(String status, String message) {
        return CommonResponse.createResponse(status, message);
    }
}
