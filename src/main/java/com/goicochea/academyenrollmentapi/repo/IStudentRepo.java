package com.goicochea.academyenrollmentapi.repo;

import com.goicochea.academyenrollmentapi.model.Student;
import reactor.core.publisher.Flux;

public interface IStudentRepo extends IGenericRepo<Student, String> {

    Flux<Student> findAllByOrderByAgeAsc();

    Flux<Student> findAllByOrderByAgeDesc();
}
