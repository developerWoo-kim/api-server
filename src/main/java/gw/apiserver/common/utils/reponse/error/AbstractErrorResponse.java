package gw.apiserver.common.utils.reponse.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "에러 응답")
@Data
@NoArgsConstructor
public abstract class AbstractErrorResponse<T> {

    @Schema(description = "상태")
    private String status;
    @Schema(description = "요청 uri")
    private String path;
    @Schema(description = "에러 코드")
    private String code;
    @Schema(description = "메시지")
    private String message;
}
