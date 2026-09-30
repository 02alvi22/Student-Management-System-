package com.Alvi.StudentManagemant.repository;

import com.Alvi.StudentManagemant.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository <Users, Long>{

 boolean existsByUsername(String username);
}
