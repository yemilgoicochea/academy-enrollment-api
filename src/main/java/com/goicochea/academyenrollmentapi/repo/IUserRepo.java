package com.goicochea.academyenrollmentapi.repo;

import com.goicochea.academyenrollmentapi.model.User;
import reactor.core.publisher.Mono;

public interface IUserRepo extends IGenericRepo<User, String> {

    Mono<User> findOneByUsername(String username);
}
