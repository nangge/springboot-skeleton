package com.nango.skeletonweb.infrastructure.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnProperty(value = "spring.swagger.enable", havingValue = "true")
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.nango.skeletonweb.interfaces"))
            .paths(PathSelectors.any())
            .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("Springboot Skeleton v1.0.0")
            .version("1.0.0")
            .build();
  }

}
