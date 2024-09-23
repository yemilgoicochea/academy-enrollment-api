package com.goicochea.academyenrollmentapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrollmentDTO {

    private String id;

    private LocalDateTime enrollmentDateTime;

    @NotNull(message = "The student is required")
    private StudentDTO student;

    @NotNull(message = "The courses are required")
    private List<CourseDTO> courses;

    private Boolean status;
}
