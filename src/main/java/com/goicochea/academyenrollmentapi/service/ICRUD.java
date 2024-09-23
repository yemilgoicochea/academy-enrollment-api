package com.goicochea.academyenrollmentapi.service;

import com.goicochea.academyenrollmentapi.pagination.PageSupport;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, ID> {

    Mono<T> save(T t);
    Mono<T> update(ID id, T t);
    Mono<T> findById(ID id);
    Flux<T> findAll();
    Mono<Boolean> delete(ID id);
    Mono<PageSupport<T>> getPage(Pageable pageable);
}
