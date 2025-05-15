package com.example.worker.client;

import com.example.worker.entity.Generation;
import com.example.worker.entity.PromptResponse;
import com.example.worker.entity.SystemInfo;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface ServerApiClient {
    @RequestLine("POST /updateGeneration")
    @Headers("Content-Type: application/json")
    void callbackToServer(@RequestBody Generation generation);

}