package com.example.worker.service;

import com.example.worker.client.ComfyUIApiClient;
import com.example.worker.entity.DeleteHistory;
import com.example.worker.entity.PromptResponse;
import com.example.worker.entity.SystemInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ImageGenerationService {

    @Value("${websocket.server.clientId}")
    private String clientId;

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    private final ComfyUIApiClient comfyUIApiClient;

    public String getJsonAsString(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filePath);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
    }

    public SystemInfo getSystemStats() {
        return comfyUIApiClient.getSystemStats();
    }

    @SneakyThrows
    public PromptResponse generateImage(String prompt) {
        // 发送请求
        return comfyUIApiClient.queuePrompt(objectMapper.readValue(getJsonAsString("api.json").replace("${text}", prompt).replace("${clientId}", clientId), Map.class));

    }

    public void delete(String promptId) {
        comfyUIApiClient.delete(new DeleteHistory(promptId));
    }

    @SneakyThrows
    public byte[] getImage(String filename) {
        return comfyUIApiClient.getImage(filename).body().asInputStream().readAllBytes();
    }
}