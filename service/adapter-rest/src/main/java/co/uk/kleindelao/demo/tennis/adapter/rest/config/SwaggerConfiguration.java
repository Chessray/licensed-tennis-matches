package co.uk.kleindelao.demo.tennis.adapter.rest.config;

import static springfox.documentation.spi.DocumentationType.OAS_30;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
    /**
     * Returns the {@link Docket} used for generating the OpenApiSpec documentation.
     *
     * @return The {@link Docket} used for generating the OpenApiSpec documentation.
     */
    @Bean
    public Docket api() {
        return new Docket(OAS_30)
            .forCodeGeneration(true)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
            .paths(PathSelectors.any())
            .build();
    }
}
