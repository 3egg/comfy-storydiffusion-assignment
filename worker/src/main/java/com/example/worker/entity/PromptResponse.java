package com.example.worker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PromptResponse(@JsonProperty("prompt_id") String promptId, Integer number) {
}
