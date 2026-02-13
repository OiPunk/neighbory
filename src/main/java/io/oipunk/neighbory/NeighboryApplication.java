package io.oipunk.neighbory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NeighboryApplication {

    /**
     * 应用入口。
     * 教学项目保持最小入口，业务能力在分层包中展开。
     */
    public static void main(String[] args) {
        SpringApplication.run(NeighboryApplication.class, args);
    }
}
