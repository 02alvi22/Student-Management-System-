package com.Alvi.StudentManagemant.service.impl;

import com.Alvi.StudentManagemant.dto.CourseDTO;
import com.Alvi.StudentManagemant.exception.GlobalExceptionHandler;
import com.Alvi.StudentManagemant.model.Courses;
import com.Alvi.StudentManagemant.repository.CourseRepository;
import com.Alvi.StudentManagemant.service.CourseService;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    CourseServiceImpl(CourseRepository courseRepository, ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {

        log.info("Creating course with code: {}", courseDTO.getCourseCode());

        Courses courses = mapper.map(courseDTO, Courses.class);
        courses.setCreatedAt(LocalDateTime.now());
        courseRepository.save(courses);
        return mapper.map(courses, CourseDTO.class);
    }

    @Override
    public boolean existsByCourseCode(String code) {
        log.info("Checking code exist: {}", code);

        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(course -> mapper.map(course, CourseDTO.class))
                .toList();
    }

    @Override
    public Page<CourseDTO> getCourses(int page, int size) {
        log.info("List of courses from: {}", page);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return courseRepository.findAll(pageRequest)
                .map(course -> mapper.map(course, CourseDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO getCourseById(Long id) {
        Courses course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No course found with id: " + id));
        return mapper.map(course, CourseDTO.class);
    }

    // ===== NEW: update a course =====
    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        log.info("Updating course with id: {}", id);

        Courses course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No course found with id: " + id));

        course.setCourseName(courseDTO.getCourseName());
        course.setCourseCode(courseDTO.getCourseCode());
        course.setDuration(courseDTO.getDuration());
        course.setFee(courseDTO.getFee());
        course.setDescription(courseDTO.getDescription());
        course.setActive(courseDTO.isActive());

        Courses saved = courseRepository.save(course);
        return mapper.map(saved, CourseDTO.class);
    }

    // ===== NEW: is this code used by a DIFFERENT course? =====
    @Override
    @Transactional(readOnly = true)
    public boolean existsByCourseCodeAndIdNot(String code, Long id) {
        return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(code, id);
    }

}