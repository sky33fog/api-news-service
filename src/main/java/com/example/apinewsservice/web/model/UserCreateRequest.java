package com.example.apinewsservice.web.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequest {

    @NotBlank(message = "Имя пользователя должно быть заполнено.")
    @Size (min = 3, max = 30,
            message = "Имя пользователя должно быть больше {min}, и меньше {max} символов.")
    private String name;

    private String password;
}
