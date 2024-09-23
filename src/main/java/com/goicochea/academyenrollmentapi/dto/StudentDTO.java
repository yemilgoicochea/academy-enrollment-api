package com.goicochea.academyenrollmentapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {

    private String id;

    @NotNull(message = "The first name is required")
    private String firstName;

    @NotNull(message = "The last name is required")
    private String lastName;

    @NotNull(message = "The DNI is required")
    @Size(min = 8, max = 8, message = "The DNI must be 8 characters")
    private String dni;

    private Integer age;
}
