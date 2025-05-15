package com.server.service;

import com.server.entity.Generation;
import com.server.repository.GenerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketScheduler {

    private final SimpMessagingTemplate messagingTemplate;
    private final GenerationRepository generationRepository;

    //@Scheduled(fixedRate = 10000) // Every 4 seconds
    public void sendScheduledMessage() {
        List<Generation> generationRepositoryAll = generationRepository.findAll();
        generationRepositoryAll.forEach(g -> g.setStatus("completed"));
        // Send to all subscribed clients
        messagingTemplate.convertAndSend("/topic/updated", generationRepositoryAll);
    }
}