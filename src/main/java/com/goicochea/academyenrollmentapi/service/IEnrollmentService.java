package com.goicochea.academyenrollmentapi.service;

import com.goicochea.academyenrollmentapi.model.Enrollment;
import reactor.core.publisher.Flux;

public interface IEnrollmentService extends ICRUD<Enrollment, String> {

    Flux<Enrollment> findByAllStudentId(String studentId);

    Flux<Enrollment> findByAllCourseId(String courseId);
}
