package gw.apiserver.common.swagger;

import com.fasterxml.classmate.TypeResolver;
import gw.apiserver.common.utils.reponse.meta.CommonErrorResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import springfox.documentation.builders.*;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.*;


// 스프링 부트를 사용하는 경우 @Configuration만, Spring이면 @EnableSwagger2도 필요
@Configuration
public class SwaggerConfig {
    private final String BASE_PACKAGE = "gw.apiserver";

    @Bean
    public Docket api() {
        String pathUri = "/api/**";
        String basePackage = "gw.apiserver";

        TypeResolver typeResolver = new TypeResolver();
        Set<String> contentType = Set.of(MediaType.APPLICATION_JSON_VALUE);

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))                // api를 탐색할 위치 설정
                .paths(PathSelectors.ant(pathUri)).build()                 // uri 시작되는 위치
                .apiInfo(apiInfo())                                 // swagger 문서 정보 세팅
//                .consumes(contentType)                              // 요청 데이터 타입
                .produces(contentType)                              // 응답 데이터 타입
                .useDefaultResponseMessages(false)            // 기본 응답 메시지 비활성화
//                .globalResponses(HttpMethod.GET, commonErrorResponses)
//                .globalResponses(HttpMethod.POST, commonErrorResponses)
//                .globalResponses(HttpMethod.PUT, commonErrorResponses)
//                .globalResponses(HttpMethod.DELETE, commonErrorResponses)
                .additionalModels(typeResolver.resolve(CommonErrorResponse.class));

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ADruck REST API GUIDE")
                .description("2023-11-28 작업 중")
                .version("1.0")
                .build();
    }

    private List<Response> setCommonErrorResponse() {
        List<Response> list = new ArrayList<>();
        list.add(new ResponseBuilder().code("401").description("Access Token 누락").build());
        list.add(new ResponseBuilder().code("403").description("Refresh Token 만료 or Refresh Token 인증 실패").build());
        return list;
    }
}
