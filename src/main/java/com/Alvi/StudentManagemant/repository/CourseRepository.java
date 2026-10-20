package com.Alvi.StudentManagemant.repository;

import com.Alvi.StudentManagemant.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses,Long> {

    boolean existsByCourseCodeIgnoreCase(String courseCode);

}
