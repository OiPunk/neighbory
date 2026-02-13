package io.oipunk.propertylab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PropertyLabApplication {

    /**
     * 应用入口。
     * 教学项目保持最小入口，业务能力在分层包中展开。
     */
    public static void main(String[] args) {
        SpringApplication.run(PropertyLabApplication.class, args);
    }
}
