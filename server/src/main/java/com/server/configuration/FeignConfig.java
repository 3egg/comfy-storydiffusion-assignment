package com.server.configuration;

import com.server.client.WorkerClient;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public WorkerClient comfyUIApiClient() {
        return Feign.builder()
                .logLevel(Logger.Level.FULL)
                .logger(new Logger.JavaLogger("Worker Client"))
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(WorkerClient.class, "http://127.0.0.1:9090");
    }
}