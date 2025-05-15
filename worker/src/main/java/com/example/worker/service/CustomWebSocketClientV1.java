package com.example.worker.service;

import com.example.worker.entity.ApiMessageUnion;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.extensions.permessage_deflate.PerMessageDeflateExtension;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.net.URI;

@Slf4j
public class CustomWebSocketClientV1 extends WebSocketClient {

    private final ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private boolean opened = false;
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private static final Draft perMessageDeflateDraft = new Draft_6455(new PerMessageDeflateExtension());

    public CustomWebSocketClientV1(URI serverUri) {
        super(serverUri, perMessageDeflateDraft);
        this.setConnectionLostTimeout(30);
        this.setTcpNoDelay(true);
        initializeScheduler();
    }

    private void initializeScheduler() {
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        threadPoolTaskScheduler.setThreadNamePrefix("websocket-reconnect-");
        threadPoolTaskScheduler.initialize();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        opened = true;
    }

    @Override
    public void onMessage(String message) {
        log.info("received message:{}", message);
        try {
            ApiMessageUnion msg = objectMapper.readValue(message, ApiMessageUnion.class);

            switch (msg.getType()) {
                case "execution_error":
                case "execution_interrupted":
                case "execution_success":
                    dispatchEvent(msg.getType(), msg.getData());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }


    private void dispatchEvent(String type, ApiMessageUnion.ApiMessageData data) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        opened = false;
        threadPoolTaskScheduler.scheduleAtFixedRate(this::reconnect, 300);
    }

    @Override
    public void onError(Exception ex) {
        log.error(ex.getMessage(), ex);
        if (!opened) {
            // Implement pollQueue() equivalent
        }
        close();
    }


    @SneakyThrows
    public void reconnect() {
        if (!isOpen()) {
            reconnectBlocking();
        }
    }

}