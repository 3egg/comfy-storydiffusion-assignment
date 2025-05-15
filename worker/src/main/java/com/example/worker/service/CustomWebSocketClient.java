package com.example.worker.service;

import com.example.worker.client.ServerApiClient;
import com.example.worker.entity.ApiMessageUnion;
import com.example.worker.entity.Generation;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.extensions.permessage_deflate.PerMessageDeflateExtension;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
@Scope("singleton")
public class CustomWebSocketClient extends WebSocketClient {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private boolean opened = false;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private static final Draft perMessageDeflateDraft = new Draft_6455(new PerMessageDeflateExtension());

    @Autowired
    private ServerApiClient serverApiClient;

    public CustomWebSocketClient(@Value("${websocket.server.url}") String serverUrl,
                                 @Value("${websocket.server.clientId}") String clientId,
                                 ThreadPoolTaskScheduler threadPoolTaskScheduler) throws URISyntaxException {
        super(new URI(serverUrl + "?clientId=" + clientId), perMessageDeflateDraft);
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.setConnectionLostTimeout(30);
        this.setTcpNoDelay(true);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        opened = true;
        log.info("WebSocket connection established");
    }

    @Override
    public void onMessage(String message) {
        log.info("Received message: {}", message);
        try {
            ApiMessageUnion msg = objectMapper.readValue(message, ApiMessageUnion.class);

            switch (msg.getType()) {
                case "execution_error", "execution_interrupted", "execution_success", "executed" -> dispatchEvent(msg.getType(), msg.getData());
                default -> log.debug("Unhandled message type: {}", msg.getType());
            }
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage(), e);
        }
    }

    private void dispatchEvent(String type, ApiMessageUnion.ApiMessageData data) {
        // Implement your event dispatching logic here
        if ("executed".equals(type)) {
            serverApiClient.callbackToServer(
                    Generation.builder()
                            .status("completed")
                            .promptId(data.getPrompt_id())
                            .imageName(data.getOutput().getImages().get(0).getFilename()).build());
        } else if ("execution_success".equals(type)) {
            serverApiClient.callbackToServer(
                    Generation.builder()
                            .status("succeed")
                            .promptId(data.getPrompt_id()).build());

        } else if ("execution_error".equals(type)) {
            serverApiClient.callbackToServer(Generation.builder()
                    .promptId(data.getPrompt_id())
                    .status("error").build());
        } else {
            serverApiClient.callbackToServer(Generation.builder()
                    .promptId(data.getPrompt_id())
                    .status("interrupted").build());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        opened = false;
        log.warn("Connection closed. Code: {}, Reason: {}, Remote: {}", code, reason, remote);
        threadPoolTaskScheduler.scheduleAtFixedRate(this::reconnect, 300);
    }

    @Override
    public void onError(Exception ex) {
        log.error("WebSocket error: {}", ex.getMessage(), ex);
        if (!opened) {
            // Implement pollQueue() equivalent
        }
        close();
    }

    @SneakyThrows
    public void reconnect() {
        if (!isOpen()) {
            log.info("Attempting to reconnect...");
            reconnectBlocking();
        }
    }

    public boolean isConnected() {
        return opened;
    }
}