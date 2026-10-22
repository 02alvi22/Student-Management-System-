package com.Alvi.StudentManagemant.service;

import com.Alvi.StudentManagemant.dto.CourseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

   CourseDTO createCourse(CourseDTO courseDTO);

   boolean existsByCourseCode(String code);

   List<CourseDTO> getAllCourses();

  Page<CourseDTO> getCourses(int page, int size);
}
