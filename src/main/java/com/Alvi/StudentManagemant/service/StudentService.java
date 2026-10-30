package com.Alvi.StudentManagemant.service;

import com.Alvi.StudentManagemant.dto.StudentDTO;
import org.springframework.data.domain.Page;

public interface StudentService {
    boolean existsByEmailIgnoreCase(String email);

    StudentDTO createStudent(StudentDTO studentDTO);

    Page<StudentDTO> getStudents(int page, int size);

    StudentDTO getStudentById(Long id);                                // NEW

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);          // NEW

    boolean existsByEmailAndIdNot(String email, Long id);              // NEW

}