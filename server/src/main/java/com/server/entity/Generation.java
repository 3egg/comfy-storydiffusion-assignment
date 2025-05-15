package com.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "generation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Generation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String promptText;
    private String promptId;
    private String status;
    private String imageName;

}
