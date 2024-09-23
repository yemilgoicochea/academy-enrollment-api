package com.goicochea.academyenrollmentapi.controller;

import com.goicochea.academyenrollmentapi.dto.StudentDTO;
import com.goicochea.academyenrollmentapi.model.Student;
import com.goicochea.academyenrollmentapi.service.IStudentService;
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

import java.net.URI;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final IStudentService studentService;

    @Qualifier("studentMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<StudentDTO>>> findAll() {
        log.info("Request to find all students");
        Flux<StudentDTO> fx = studentService.findAll().map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<StudentDTO>> findById(@PathVariable("id") String id) {
        log.info("Request to find student by id: {}", id);
        return studentService.findById(id)
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<StudentDTO>> save(@Valid @RequestBody StudentDTO studentDTO) {
        log.info("Request to save student: {}", studentDTO);
        return studentService.save(convertToDocument(studentDTO))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.created(URI.create("/students/".concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<StudentDTO>> update(@Valid @PathVariable("id") String id, @RequestBody StudentDTO studentDTO) {
        log.info("Request to update student with id: {}", id);
        return Mono.just(convertToDocument(studentDTO))
                .map(e -> {
                        e.setId(id);
                        return e;
                })
                .flatMap(e -> studentService.update(id, e))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        log.info("Request to delete student with id: {}", id);
        return studentService.delete(id)
                .map(e -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/age")
    public Mono<ResponseEntity<Flux<StudentDTO>>> findByAllOrderByAge(@RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        log.info("Request to find all students ordered by age: {}", sort);
        Flux<StudentDTO> fx;
        if ("DESC".equalsIgnoreCase(sort)) {
            fx = studentService.findAllByOrderByAgeDesc().map(this::convertToDto);
        } else {
            fx = studentService.findAllByOrderByAgeAsc().map(this::convertToDto);
        }
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private StudentDTO convertToDto(Student model) {
        return modelMapper.map(model, StudentDTO.class);
    }

    private Student convertToDocument(StudentDTO dto){
        return modelMapper.map(dto, Student.class);
    }
}
