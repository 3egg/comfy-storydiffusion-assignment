package com.server.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The data loader. This is a simple component that is used to populate the database with some data.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("Populating database...");

        log.info("Database populated!");
    }

}
