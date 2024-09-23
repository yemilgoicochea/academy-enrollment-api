package com.goicochea.academyenrollmentapi.config;

import com.goicochea.academyenrollmentapi.dto.CourseDTO;
import com.goicochea.academyenrollmentapi.model.Course;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean(name = "defaultMapper")
    public ModelMapper defaultMapper() {
        return new ModelMapper();
    }

    @Bean(name = "courseMapper")
    public ModelMapper courseMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //Escritura
        mapper.createTypeMap(CourseDTO.class, Course.class)
                .addMapping(CourseDTO::getName, Course::setName)
                .addMapping(CourseDTO::getShortName, Course::setShortName)
                .addMapping(CourseDTO::getStatus, Course::setStatus);

        //Lectura
        mapper.createTypeMap(Course.class, CourseDTO.class)
                .addMapping(Course::getName, CourseDTO::setName)
                .addMapping(Course::getShortName, CourseDTO::setShortName)
                .addMapping(Course::getStatus, CourseDTO::setStatus);

        return new ModelMapper();
    }

    @Bean(name = "studentMapper")
    public ModelMapper studentMapper() {
        return new ModelMapper();
    }

    @Bean(name = "enrollmentMapper")
    public ModelMapper enrollmentMapper() {
        return new ModelMapper();
    }
}
