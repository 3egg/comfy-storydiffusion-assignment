package com.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PromptResponse(@JsonProperty("prompt_id") String promptId, Integer number) {
}
