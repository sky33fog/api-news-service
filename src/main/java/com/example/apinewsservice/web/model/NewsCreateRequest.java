package com.example.apinewsservice.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class NewsCreateRequest {

    @NotBlank(message = "Заголовок должен быть указан!")
    private String title;

    private String description;

    @NotNull(message = "ID категории должно быть указано!")
    @Positive(message = "ID категории должно быть больше 0!")
    private Long  categoryId;
}
