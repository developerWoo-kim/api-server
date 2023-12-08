package gw.apiserver.common.utils.reponse.exception;

import gw.apiserver.common.utils.reponse.code.CommonError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GlobalApiException extends RuntimeException{
    private final CommonError errorCode;
}
