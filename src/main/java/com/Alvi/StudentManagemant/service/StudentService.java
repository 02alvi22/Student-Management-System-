package com.Alvi.StudentManagemant.service;

import com.Alvi.StudentManagemant.dto.StudentDTO;

public interface StudentService {
    boolean existsByEmailIgnoreCase(String email);

    StudentDTO createStudent(StudentDTO studentDTO);

}
