package com.Alvi.StudentManagemant.service.impl;

import com.Alvi.StudentManagemant.dto.CourseDTO;
import com.Alvi.StudentManagemant.model.Courses;
import com.Alvi.StudentManagemant.repository.CourseRepository;
import com.Alvi.StudentManagemant.service.CourseService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // ADDED
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    CourseServiceImpl(CourseRepository courseRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Courses courses = mapper.map(courseDTO, Courses.class);
        courses.setCreatedAt(LocalDateTime.now()); // ADDED: createdAt can't be null
        courseRepository.save(courses);
        return mapper.map(courses, CourseDTO.class);
    }

    @Override
    public boolean existsByCourseCode(String code) {
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> mapper.map(course, CourseDTO.class))
                .toList();
    }

}