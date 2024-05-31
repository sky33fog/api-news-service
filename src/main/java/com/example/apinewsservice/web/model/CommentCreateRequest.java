package com.example.apinewsservice.web.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CommentCreateRequest {

    @NotNull(message = "Текст комментария должен быть указан!")
    private String text;

    @NotNull(message = "ID новости должно быть указано!")
    @Positive(message = "ID новости должно быть больше 0!")
    private Long newsId;

    @NotNull(message = "ID пользователя должно быть указано!")
    @Positive(message = "ID пользователя должно быть больше 0!")
    private Long userId;
}
