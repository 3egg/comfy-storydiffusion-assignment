package com.server.client;

import com.server.entity.Generation;
import com.server.entity.PromptResponse;
import com.server.entity.SystemInfo;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import org.springframework.web.bind.annotation.RequestBody;

public interface WorkerClient {
    @RequestLine("POST /api/v1/prompt")
    @Headers("Content-Type: application/json")
    PromptResponse promptTextToImage(@RequestBody Generation generation);


    @RequestLine("GET /api/v1/system-stats")
    @Headers("Content-Type: application/json")
    SystemInfo getSystemStats();


    @RequestLine("POST /api/v1/delete")
    @Headers("Content-Type: application/json")
    void delete(Generation generation);

    @RequestLine("GET /api/v1/view?filename={filename}")
    Response getImage(@Param("filename") String filename);
}