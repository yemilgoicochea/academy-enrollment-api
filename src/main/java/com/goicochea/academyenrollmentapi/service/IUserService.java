package com.goicochea.academyenrollmentapi.service;

import com.goicochea.academyenrollmentapi.model.User;
import reactor.core.publisher.Mono;

public interface IUserService extends ICRUD<User, String>{
    Mono<User> saveHash(User user);
    Mono<com.goicochea.academyenrollmentapi.security.User> searchByUser(String username);
}
