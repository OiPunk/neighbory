package io.oipunk.neighbory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NeighboryApplication {

    /**
     * Application entry point.
     * Keep bootstrap minimal; business capabilities live in layered packages.
     */
    public static void main(String[] args) {
        SpringApplication.run(NeighboryApplication.class, args);
    }
}
