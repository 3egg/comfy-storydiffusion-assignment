package com.example.worker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Generation {
    private Long id;

    private String promptText;
    private String promptId;
    private String status;
    private String imageName;

}
