package com.server.controller;

import com.server.entity.Generation;
import com.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerController {


    private final ServerService serverService;

    @PostMapping("updateGeneration")
    public void updateGeneration(@RequestBody Generation generation) {
        serverService.updateGeneration(generation);
    }

    @GetMapping("/generations")
    public List<Generation> generations() {
        return serverService.generations();
    }
}

