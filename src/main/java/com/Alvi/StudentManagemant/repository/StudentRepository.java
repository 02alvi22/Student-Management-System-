package com.Alvi.StudentManagemant.repository;

import com.Alvi.StudentManagemant.model.Students;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Students, Long> {
    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);   // NEW

    Page<Students> findByActiveTrue(Pageable pageable);


}