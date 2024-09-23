package com.goicochea.academyenrollmentapi.service.impl;

import com.goicochea.academyenrollmentapi.model.Enrollment;
import com.goicochea.academyenrollmentapi.repo.IEnrollmentRepo;
import com.goicochea.academyenrollmentapi.repo.IGenericRepo;
import com.goicochea.academyenrollmentapi.service.IEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl extends CRUDImpl<Enrollment, String> implements IEnrollmentService {

    private final IEnrollmentRepo enrollmentRepo;

    @Override
    protected IGenericRepo<Enrollment, String> getRepo() {
        return enrollmentRepo;
    }

    @Override
    public Flux<Enrollment> findByAllStudentId(String studentId) {
        return getRepo().findAll().filter(e -> e.getStudent().getId().equals(studentId));
    }

    @Override
    public Flux<Enrollment> findByAllCourseId(String courseId) {
        return getRepo().findAll().filter(e -> e.getCourses().stream().anyMatch(c -> c.getId().equals(courseId)));
    }
}
