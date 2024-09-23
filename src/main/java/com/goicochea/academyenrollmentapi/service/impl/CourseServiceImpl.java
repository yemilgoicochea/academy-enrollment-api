package com.goicochea.academyenrollmentapi.service.impl;

import com.goicochea.academyenrollmentapi.model.Course;
import com.goicochea.academyenrollmentapi.repo.ICourseRepo;
import com.goicochea.academyenrollmentapi.repo.IGenericRepo;
import com.goicochea.academyenrollmentapi.service.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends CRUDImpl<Course, String> implements ICourseService {

    private final ICourseRepo courseRepo;

    @Override
    protected IGenericRepo<Course, String> getRepo() {
        return courseRepo;
    }
}
