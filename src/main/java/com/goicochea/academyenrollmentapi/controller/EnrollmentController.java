package com.goicochea.academyenrollmentapi.controller;


import com.goicochea.academyenrollmentapi.dto.EnrollmentDTO;
import com.goicochea.academyenrollmentapi.model.Enrollment;
import com.goicochea.academyenrollmentapi.service.IEnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;

    @Qualifier("enrollmentMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<EnrollmentDTO>>> findAll() {
        log.info("Request to find all enrollments");
        Flux<EnrollmentDTO> fx = enrollmentService.findAll().map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @GetMapping("/{studentId}")
    public Mono<ResponseEntity<Flux<EnrollmentDTO>>> findByStudentId(String studentId) {
        log.info("Request to find enrollments by student id: {}", studentId);
        Flux<EnrollmentDTO> fx = enrollmentService.findByAllStudentId(studentId).map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{courseId}")
    public Mono<ResponseEntity<Flux<EnrollmentDTO>>> findByCourseId(String courseId) {
        log.info("Request to find enrollments by course id: {}", courseId);
        Flux<EnrollmentDTO> fx = enrollmentService.findByAllCourseId(courseId).map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<EnrollmentDTO>> save(@Valid @RequestBody EnrollmentDTO enrollmentDTO) {
        log.info("Request to save enrollment: {}", enrollmentDTO);
        return enrollmentService.save(convertToDocument(enrollmentDTO))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.created(null)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

    private EnrollmentDTO convertToDto(Enrollment model) {
        return modelMapper.map(model, EnrollmentDTO.class);
    }

    private Enrollment convertToDocument(EnrollmentDTO dto){
        if(dto.getEnrollmentDateTime() == null){
            dto.setEnrollmentDateTime(LocalDateTime.now());
        }
        return modelMapper.map(dto, Enrollment.class);
    }
}
