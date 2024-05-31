package com.example.apinewsservice.web.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {

    @NotNull(message = "Размер страницы вывода должен быть указан. Пример: pageSize=4")
    private Integer pageSize;

    @NotNull(message = "Номер страницы вывода должен быть указан. Пример: pageNumber=0")
    private Integer pageNumber;
}
