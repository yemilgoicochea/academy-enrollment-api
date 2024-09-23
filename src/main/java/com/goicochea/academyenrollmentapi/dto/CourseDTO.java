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
public class CourseDTO {

    private String id;

    @NotNull(message = "The course name is required")
    @Size(min = 2, max = 50, message = "The course name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "The course short name is required")
    private String shortName;

    private Boolean status;
}
