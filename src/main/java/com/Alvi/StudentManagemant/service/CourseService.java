package com.Alvi.StudentManagemant.service;

import com.Alvi.StudentManagemant.dto.CourseDTO;

import java.util.List;

public interface CourseService {

   CourseDTO createCourse(CourseDTO courseDTO);

   boolean existsByCourseCode(String code);

   List<CourseDTO> getAllCourses();

}
