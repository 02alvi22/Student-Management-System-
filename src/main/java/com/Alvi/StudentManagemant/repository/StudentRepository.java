package com.Alvi.StudentManagemant.repository;

import com.Alvi.StudentManagemant.model.Students;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
