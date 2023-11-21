package gw.apiserver.common.utils.reponse.error;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class AbstractErrorResponse<T> {
    private String status;
    private String path;
    private String code;
    private String message;
}
