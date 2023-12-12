package gw.apiserver.common.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 글로벌 Config Value 객체
 * YML 파일의 글로벌 설정을 가져 옵니다.
 *
 * @author gwkim
 * @since 2023.08.24
 * @version 1.0
 */
@Component
@Getter @Setter
@NoArgsConstructor
public class CommonGlobalsConfigValue {
    @Value("${Globals.rootPath}")
    private String rootPath;

    @Value("${Globals.AesSecretKey}")
    private String aesSecretKey;

    @Value("${Globals.fileStorePath}")
    private String fileStorePath;
}
