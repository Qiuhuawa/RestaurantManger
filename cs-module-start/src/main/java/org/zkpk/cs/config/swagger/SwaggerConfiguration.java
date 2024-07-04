package org.zkpk.cs.config.swagger;


import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @Description: swagger2配置 默认地址http://localhost:8080/swagger-ui.html
 * @author HUCHAO
 * @date 2018-10-19 12:06:28
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"org.zkpk.cs.controller"})  //需要扫描的包路径
public class SwaggerConfiguration {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
//                .groupName("bigdata-api")
                .select()   // 选择那些路径和api会生成document
                //.apis(RequestHandlerSelectors.any())  // 对所有api进行监控
                .apis(RequestHandlerSelectors.basePackage("org.zkpk.cs.controller"))
                //.paths(paths())	// 对指定路径进行监控
                .paths(PathSelectors.any())   // 对所有路径进行监控
                .build();
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
	}
	
	private Predicate<String> paths() {
        return or(regex("/**"));
    }

    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("clientId", "客户端ID", "header"),
                new ApiKey("clientSecret", "客户端秘钥", "header"),
                new ApiKey("accessToken", "客户端访问标识", "header")
                );
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("/*.*"))
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("clientId", authorizationScopes),
                new SecurityReference("clientSecret", authorizationScopes),
                new SecurityReference("accessToken", authorizationScopes)
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("比赛系统API")
                .description("比赛系统API")
                .termsOfServiceUrl("http://localhost/") //服务条款网址
                .version("1.0")
//                .license("License Version 1.0")
//                .licenseUrl("http://localhost/lab-web-bigdata")
                .contact(new Contact("普开数据", "http://www.zkpk.org/", "shibiao@zkpk.org"))
                .build();
    }

}
