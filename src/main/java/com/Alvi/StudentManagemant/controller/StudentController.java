package com.Alvi.StudentManagemant.controller;


import com.Alvi.StudentManagemant.dto.StudentDTO;
import com.Alvi.StudentManagemant.service.StudentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController (StudentService studentService){
        this.studentService = studentService;

    }

    @GetMapping("/new")
    public String showCreateStudent(Model model){
        log.info("Get /new - showing create student page");
        model.addAttribute("studentDto", new StudentDTO());

        return "add-student";
    }

    @GetMapping("/list")
    public String listStudent(@RequestParam(defaultValue="0")int page,
                              @RequestParam(defaultValue="3")int size,
                              Model model){
        log.info("Get /list - showing list student page");
        Page<StudentDTO> students = studentService.getStudents(page, size);
        model.addAttribute("students", students);
        return "students";
    }


    @PostMapping("/save")
    public String createStudent(@Valid @ModelAttribute("studentDto") StudentDTO studentDTO,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        log.info("Post /save - create student request received");
        if (bindingResult.hasErrors()){
            return "add-student";
        }

        if(studentService.existsByEmailIgnoreCase(studentDTO.getEmail())){
            log.error("Post /save - email must be unique");

            bindingResult.rejectValue("email", null, "email must be unique");
            return "add-student";
        }

        studentService.createStudent(studentDTO);
        redirectAttributes.addFlashAttribute("message", "Student is added successfully!");
        return"redirect:/students/list";
    }

    // ===== NEW: view one student =====
    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        log.info("Get /students/view/{} - showing student details", id);
        model.addAttribute("student", studentService.getStudentById(id));
        return "view-student";
    }

    // ===== NEW: show the edit form =====
    @GetMapping("/edit/{id}")
    public String showEditStudent(@PathVariable Long id, Model model) {
        log.info("Get /students/edit/{} - showing edit student page", id);
        model.addAttribute("studentDto", studentService.getStudentById(id));
        return "edit-student";
    }

    // ===== NEW: save the edited student =====
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute("studentDto") StudentDTO studentDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        log.info("Post /students/edit/{} - update student request received", id);
        studentDTO.setId(id); // keep the id so the form action still works if we re-show the page

        if (bindingResult.hasErrors()) {
            return "edit-student";
        }

        if (studentService.existsByEmailAndIdNot(studentDTO.getEmail(), id)) {
            bindingResult.rejectValue("email", null, "email must be unique");
            return "edit-student";
        }

        studentService.updateStudent(id, studentDTO);
        redirectAttributes.addFlashAttribute("message", "Student updated successfully!");
        return "redirect:/students/list";
    }
}