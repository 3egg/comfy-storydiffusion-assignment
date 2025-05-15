package com.server.controller;

import com.server.entity.Generation;
import com.server.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final WorkerService workerService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Generation promptTextToImage(String message) {
        return workerService.promptTextToImage(Generation.builder().promptText(message).status("pending").build());
    }

    @MessageMapping("/delete")
    @SendTo("/topic/delete")
    public Generation delete(String promptId) {
        return workerService.delete(Generation.builder().promptId(promptId).status("deleted").build());
    }
}