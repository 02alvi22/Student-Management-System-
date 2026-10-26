package com.Alvi.StudentManagemant.repository;

import com.Alvi.StudentManagemant.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses,Long> {

    boolean existsByCourseCodeIgnoreCase(String courseCode);
    boolean existsByCourseCodeIgnoreCaseAndIdNot(String courseCode, Long id);   // new

    Page<Courses> findByActiveTrue(Pageable pageable);
}
