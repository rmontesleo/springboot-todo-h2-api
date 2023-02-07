package com.demo.todoapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record PutTodo(
        @NotBlank( message = "Title must not be null nor empty/blank value")
        @Size( min = 1, max = 50, message = "Title most be between 1 and 50 characters")
        String title,

        @NotBlank(  message = "Description must not be null nor empty/blank value" )
        @Size( min = 1, max = 200, message = "Description most be between 1 and 200 characters")
        String description,

        @NotNull(  message = "Status must not be null" )
        Boolean completed
) {
}
