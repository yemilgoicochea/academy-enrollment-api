package com.goicochea.academyenrollmentapi.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericRepo<T, ID> extends ReactiveMongoRepository<T, ID> {
}
