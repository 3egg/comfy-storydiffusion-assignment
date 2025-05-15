package com.server.service;

import com.server.entity.Generation;
import com.server.repository.GenerationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ServerService {

    private final GenerationRepository generationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void updateGeneration(Generation generation) {
        Generation dbGeneration = generationRepository.findFirstByPromptIdOrderByIdDesc(generation.getPromptId()).orElseThrow();
        dbGeneration.setStatus(generation.getStatus());
        dbGeneration.setImageName(generation.getImageName());
        generationRepository.save(generation);
        messagingTemplate.convertAndSend("/topic/updated", dbGeneration);

    }

    public List<Generation> generations() {
        return generationRepository.findAll();
    }
}
