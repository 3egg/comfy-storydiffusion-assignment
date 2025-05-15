package com.example.worker.configuration;

import com.example.worker.service.CustomWebSocketClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.net.URISyntaxException;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig {

    @Bean
    public ThreadPoolTaskScheduler webSocketTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("websocket-reconnect-");
        return scheduler;
    }

    @Bean(initMethod = "connect")
    public CustomWebSocketClient customWebSocketClient(
            @Value("${websocket.server.url}") String serverUrl,
            @Value("${websocket.server.clientId}") String clientId,
            ThreadPoolTaskScheduler webSocketTaskScheduler) throws URISyntaxException {
        return new CustomWebSocketClient(serverUrl, clientId, webSocketTaskScheduler);
    }
}