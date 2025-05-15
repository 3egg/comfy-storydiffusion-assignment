package com.server.repository;

import com.server.entity.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenerationRepository extends JpaRepository<Generation, Long> {

    Optional<Generation> findFirstByPromptIdOrderByIdDesc(String promptId);
}
