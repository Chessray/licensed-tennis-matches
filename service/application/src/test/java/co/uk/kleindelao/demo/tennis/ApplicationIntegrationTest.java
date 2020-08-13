package co.uk.kleindelao.demo.tennis;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = Application.class, webEnvironment = RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class ApplicationIntegrationTest {
    @Autowired
    private Application application;

    @Autowired
    private ApplicationContext context;

    @Test
    void applicationShouldStart() {
        then(application).isNotNull();
        then(context).isNotNull();
    }
}