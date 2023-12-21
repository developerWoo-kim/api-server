package gw.apiserver.common.utils.reponse.meta;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.List;

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
    @Schema(description = "에러 코드")
    private String code;
    @Schema(description = "에러 메시지")
    private String message;
    @Schema(description = "요청 URI")
    private String path;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    @Schema(description = "유효성 검사 오류")
    public static class ValidationError {
        @Schema(description = "오류가 발생한 필드")
        private final String field;
        @Schema(description = "오류 메시지")
        private final String message;
        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
