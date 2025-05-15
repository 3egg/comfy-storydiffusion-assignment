package com.server.service;

import com.server.client.WorkerClient;
import com.server.entity.Generation;
import com.server.entity.PromptResponse;
import com.server.repository.GenerationRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WorkerService {

    private final GenerationRepository generationRepository;
    private final WorkerClient workerClient;
    private final SimpMessagingTemplate messagingTemplate;

    public Generation promptTextToImage(Generation generation) {
        log.info("call worker to promptTextToImage:{}", generation);
        PromptResponse promptResponse = workerClient.promptTextToImage(generation);
        generation.setPromptId(promptResponse.promptId());
        return generationRepository.save(generation);
    }

    public Generation delete(Generation interrupted) {
        log.info("call worker to interrupted:{}", interrupted);
        Generation generation = generationRepository.findFirstByPromptIdOrderByIdDesc(interrupted.getPromptId()).orElseThrow();
        generation.setStatus("deleted");
        workerClient.delete(generation);
        generationRepository.save(generation);
        messagingTemplate.convertAndSend("/topic/updated", generation);
        return generation;
    }

    @SneakyThrows
    public byte[] getImage(String filename) {
        return workerClient.getImage(filename).body().asInputStream().readAllBytes();
    }
}
