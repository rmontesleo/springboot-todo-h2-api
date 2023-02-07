package com.demo.todoapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserRequest(
        @NotBlank( message = "Username must not be null nor empty/blank value"  )
        @Size( min = 4, max = 50, message = "Username length must be between 4 and 50" )
        String userName,

        @NotBlank(  message = "Password must not be null nor empty/blank value" )
        String password
) {
}
