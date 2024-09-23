package com.goicochea.academyenrollmentapi.service;

import com.goicochea.academyenrollmentapi.model.Student;
import reactor.core.publisher.Flux;

public interface IStudentService extends ICRUD<Student, String> {

    Flux<Student> findAllByOrderByAgeAsc();

    Flux<Student> findAllByOrderByAgeDesc();
}
