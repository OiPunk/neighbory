package io.oipunk.propertylab;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class PropertyLabApplicationTest {

    @Test
    void mainShouldDelegateToSpringApplicationRun() {
        String[] args = new String[] {"--spring.main.web-application-type=none"};
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            PropertyLabApplication.main(args);
            mocked.verify(() -> SpringApplication.run(PropertyLabApplication.class, args));
        }
    }
}
