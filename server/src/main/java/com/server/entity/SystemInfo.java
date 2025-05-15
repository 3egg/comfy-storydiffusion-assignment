package com.server.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record SystemInfo(
    @JsonProperty("system") SystemSpec system,
    @JsonProperty("devices") List<DeviceInfo> devices
) {
    public record SystemSpec(
        @JsonProperty("os") String os,
        @JsonProperty("ram_total") long ramTotal,
        @JsonProperty("ram_free") long ramFree,
        @JsonProperty("comfyui_version") String comfyuiVersion,
        @JsonProperty("python_version") String pythonVersion,
        @JsonProperty("pytorch_version") String pytorchVersion,
        @JsonProperty("embedded_python") boolean embeddedPython,
        @JsonProperty("argv") List<String> argv
    ) {}

    public record DeviceInfo(
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("index") int index,
        @JsonProperty("vram_total") long vramTotal,
        @JsonProperty("vram_free") long vramFree,
        @JsonProperty("torch_vram_total") long torchVramTotal,
        @JsonProperty("torch_vram_free") long torchVramFree
    ) {}
}