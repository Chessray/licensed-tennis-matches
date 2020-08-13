package co.uk.kleindelao.demo.tennis.adapter.rest.config;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

class SwaggerConfigurationTest {
    private final SwaggerConfiguration configuration = new SwaggerConfiguration();

    @Test
    void shouldProvideOpenApiTypeDocumentation() {
        final var docket = configuration.api();
        then(docket).isNotNull();
        then(docket.getDocumentationType().getName()).isEqualToIgnoringCase("OpenApi");
    }
}