package com.Alvi.StudentManagemant.service.impl;


import com.Alvi.StudentManagemant.dto.StudentDTO;
import com.Alvi.StudentManagemant.model.Students;
import com.Alvi.StudentManagemant.repository.StudentRepository;
import com.Alvi.StudentManagemant.service.StudentService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);


    private final StudentRepository studentRepository;
    private final ModelMapper mapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper mapper){
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        log.info("email from create student");
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("Saving student data");
        Students students = mapper.map(studentDTO, Students.class);
        students.setCreatedAt(LocalDateTime.now());
        Students saved = studentRepository.save(students);
        return mapper.map(saved, StudentDTO.class);
    }
}