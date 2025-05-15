package com.example.worker.controller;

import com.example.worker.entity.Generation;
import com.example.worker.entity.PromptResponse;
import com.example.worker.entity.SystemInfo;
import com.example.worker.service.ImageGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ComfyController {

    private final ImageGenerationService imageGenerationService;

    @GetMapping("system-stats")
    public SystemInfo getSystemStats() {
        return imageGenerationService.getSystemStats();
    }

    @PostMapping("prompt")
    public PromptResponse promptTextToImage(@RequestBody Generation generation) {
        return imageGenerationService.generateImage(generation.getPromptText());
    }

    @PostMapping("delete")
    public void delete(@RequestBody Generation generation) {
        imageGenerationService.delete(generation.getPromptId());
    }
}
