package com.example.apinewsservice.web.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryCreateRequest {

    @NotBlank(message = "Название категории должно быть указано.")
    private String rubric;
}
