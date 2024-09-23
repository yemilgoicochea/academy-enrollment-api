package com.goicochea.academyenrollmentapi.handler;

import com.goicochea.academyenrollmentapi.dto.EnrollmentDTO;
import com.goicochea.academyenrollmentapi.model.Enrollment;
import com.goicochea.academyenrollmentapi.service.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@RequiredArgsConstructor
public class EnrollmentHandler {
    
    private final IEnrollmentService service;

    @Qualifier("enrollmentMapper")
    private final ModelMapper modelMapper;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll().map(this::convertToDto), EnrollmentDTO.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<EnrollmentDTO> monoEnrollmentDTO = request.bodyToMono(EnrollmentDTO.class);

        return monoEnrollmentDTO
                .flatMap(e -> service.save(convertToDocument(e)))
                .map(this::convertToDto)
                .flatMap(e -> ServerResponse
                        .created(URI.create(request.uri().toString().concat("/").concat(e.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromValue(e))
                );
    }

    private EnrollmentDTO convertToDto(Enrollment model) {
        return modelMapper.map(model, EnrollmentDTO.class);
    }

    private Enrollment convertToDocument(EnrollmentDTO dto) {
        return modelMapper.map(dto, Enrollment.class);
    }
}
