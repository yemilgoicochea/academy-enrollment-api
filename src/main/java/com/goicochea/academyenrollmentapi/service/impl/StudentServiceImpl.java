package com.goicochea.academyenrollmentapi.service.impl;

import com.goicochea.academyenrollmentapi.model.Student;
import com.goicochea.academyenrollmentapi.repo.IGenericRepo;
import com.goicochea.academyenrollmentapi.repo.IStudentRepo;
import com.goicochea.academyenrollmentapi.service.IStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends CRUDImpl<Student, String> implements IStudentService {

    private final IStudentRepo studentRepo;


    @Override
    protected IGenericRepo<Student, String> getRepo() {
        return studentRepo;
    }

    @Override
    public Flux<Student> findAllByOrderByAgeAsc() {
        return studentRepo.findAllByOrderByAgeAsc();
    }

    @Override
    public Flux<Student> findAllByOrderByAgeDesc() {
        return studentRepo.findAllByOrderByAgeDesc();
    }
}
