package com.example.worker.client;

import com.example.worker.entity.DeleteHistory;
import com.example.worker.entity.PromptResponse;
import com.example.worker.entity.SystemInfo;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface ComfyUIApiClient {
    @RequestLine("POST /prompt")
    @Headers("Content-Type: application/json")
    PromptResponse queuePrompt(@RequestBody Object prompt);


    @RequestLine("GET /system_stats")
    @Headers("Content-Type: application/json")
    SystemInfo getSystemStats();

    @RequestLine("POST /history")
    @Headers("Content-Type: application/json")
    void delete(@Param("promptId") DeleteHistory promptId);
}