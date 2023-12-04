package gw.apiserver.common.utils.reponse.meta;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class AbstractCommonResponse {
    private String status;
    private String message;
}
