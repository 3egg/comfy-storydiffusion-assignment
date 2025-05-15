package com.server.client;

import com.server.entity.Generation;
import com.server.entity.PromptResponse;
import com.server.entity.SystemInfo;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface WorkerClient {
    @RequestLine("POST /prompt")
    @Headers("Content-Type: application/json")
    PromptResponse promptTextToImage(@RequestBody Generation generation);


    @RequestLine("GET /system-stats")
    @Headers("Content-Type: application/json")
    SystemInfo getSystemStats();


    @RequestLine("POST /delete")
    @Headers("Content-Type: application/json")
    void delete(Generation generation);
}