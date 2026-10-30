package com.Alvi.StudentManagemant.service.impl;


import com.Alvi.StudentManagemant.dto.StudentDTO;
import com.Alvi.StudentManagemant.model.Students;
import com.Alvi.StudentManagemant.repository.StudentRepository;
import com.Alvi.StudentManagemant.service.StudentService;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<StudentDTO> getStudents(int page, int size) {

        log.info("List of students from: {}", page);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return studentRepository.findAll(pageRequest)
                .map(student -> mapper.map(student, StudentDTO.class));
    }

    // ===== NEW: get one student =====
    @Override
    @Transactional(readOnly = true)
    public StudentDTO getStudentById(Long id) {
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No student found with id: " + id));
        return mapper.map(student, StudentDTO.class);
    }

    // ===== NEW: update a student =====
    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        log.info("Updating student with id: {}", id);

        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No student found with id: " + id));

        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.setAddress(studentDTO.getAddress());
        student.setActive(studentDTO.isActive());

        Students saved = studentRepository.save(student);
        return mapper.map(saved, StudentDTO.class);
    }

    // ===== NEW: is this email used by a DIFFERENT student? =====
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return studentRepository.existsByEmailIgnoreCaseAndIdNot(email, id);
    }
}