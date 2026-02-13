package io.oipunk.neighbory;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class NeighboryApplicationTest {

    @Test
    void mainShouldDelegateToSpringApplicationRun() {
        String[] args = new String[] {"--spring.main.web-application-type=none"};
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {
            NeighboryApplication.main(args);
            mocked.verify(() -> SpringApplication.run(NeighboryApplication.class, args));
        }
    }
}
