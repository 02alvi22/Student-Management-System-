package com.Alvi.StudentManagemant.service;

import com.Alvi.StudentManagemant.dto.StudentDTO;
import org.springframework.data.domain.Page;

public interface StudentService {
    boolean existsByEmailIgnoreCase(String email);

    StudentDTO createStudent(StudentDTO studentDTO);

    Page<StudentDTO> getStudents(int page, int size);


}
