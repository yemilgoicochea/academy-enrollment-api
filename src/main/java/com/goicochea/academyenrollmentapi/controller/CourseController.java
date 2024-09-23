package com.goicochea.academyenrollmentapi.controller;

import com.goicochea.academyenrollmentapi.dto.CourseDTO;
import com.goicochea.academyenrollmentapi.model.Course;
import com.goicochea.academyenrollmentapi.service.ICourseService;
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
@RequestMapping("/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {

    private final ICourseService courseService;

    @Qualifier("courseMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<CourseDTO>>> findAll() {
        log.info("Request to find all courses");
        Flux<CourseDTO> fx = courseService.findAll().map(this::convertToDto);
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CourseDTO>> findById(@PathVariable("id") String id) {
        log.info("Request to find course by id: {}", id);
        return courseService.findById(id)
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<CourseDTO>> save(@Valid @RequestBody CourseDTO courseDTO) {
        log.info("Request to save course: {}", courseDTO);
        return courseService.save(convertToDocument(courseDTO))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.created(URI.create("/students/".concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                );
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CourseDTO>> update(@Valid @PathVariable("id") String id, @RequestBody CourseDTO courseDTO) {
        log.info("Request to update course with id: {}", id);
        return Mono.just(convertToDocument(courseDTO))
                .map(e -> {
                    e.setId(id);
                    return e;
                })
                .flatMap(e -> courseService.update(id, e))
                .map(this::convertToDto)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        log.info("Request to delete course with id: {}", id);
        return courseService.delete(id)
                .map(e -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private CourseDTO convertToDto(Course model) {
        return modelMapper.map(model, CourseDTO.class);
    }

    private Course convertToDocument(CourseDTO dto){
        return modelMapper.map(dto, Course.class);
    }
}
