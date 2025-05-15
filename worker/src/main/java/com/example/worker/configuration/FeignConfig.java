package com.example.worker.configuration;

import com.example.worker.client.ComfyUIApiClient;
import com.example.worker.client.ServerApiClient;
import feign.Feign;
import feign.Logger;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ComfyUIApiClient comfyUIApiClient() {
        return Feign.builder()
                .logLevel(Logger.Level.FULL)
                .logger(new Logger.JavaLogger("ComfyUI Client"))
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ComfyUIApiClient.class, "http://127.0.0.1:8000");
    }

    @Bean
    public ServerApiClient serverApiClient() {
        return Feign.builder()
                .logLevel(Logger.Level.FULL)
                .logger(new Logger.JavaLogger("ComfyUI Client"))
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .target(ServerApiClient.class, "http://127.0.0.1:8080");
    }
}