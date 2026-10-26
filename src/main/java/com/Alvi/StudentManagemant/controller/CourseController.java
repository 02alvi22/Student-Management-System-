package com.Alvi.StudentManagemant.controller;


import com.Alvi.StudentManagemant.dto.CourseDTO;
import com.Alvi.StudentManagemant.exception.GlobalExceptionHandler;
import com.Alvi.StudentManagemant.repository.CourseRepository;
import com.Alvi.StudentManagemant.service.CourseService;
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
@RequestMapping("/course")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    private final CourseService courseService;

    CourseController(CourseService courseService){
        this.courseService = courseService;

    }

    @GetMapping("/new")
    public String showCreateCourse(Model model){
        log.info("Get /course/new - showing create course page.");

        model.addAttribute("courseDto", new CourseDTO());
        return "add-course";
    }

    @GetMapping("/list")
    public String listCourses(@RequestParam(defaultValue="0")int page,
                              @RequestParam(defaultValue="3")int size,
                              Model model) {

        log.info("Get /course/new - showing course list page.");
        Page<CourseDTO> courses = courseService.getCourses(page, size);
        model.addAttribute("courses", courses);
        return "courses";
    }

    @PostMapping
    public String createCourses (@Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        log.info("post /course - create course request received.");


        if (bindingResult.hasErrors()) {
            log.error("post /course - page return due to validation error.");
            return "add-course";
        }

        if (courseService.existsByCourseCode(courseDTO.getCourseCode())) {
            log.error("post /course - Code must be unique.");

            bindingResult.rejectValue("courseCode", null, "Code must be unique");
            return "add-course";
        }

        courseService.createCourse(courseDTO);
        redirectAttributes.addFlashAttribute("message", "Course created successfully ");
        log.info("post /course - Course successfully created.");

        return "redirect:/course/list";

    }
    @GetMapping ("/{id}")
    public  String getCourseById(@PathVariable Long id, Model model){
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "view-course";
    }

    // ===== NEW: show the edit form =====
    @GetMapping("/{id}/edit")
    public String showEditCourse(@PathVariable Long id, Model model) {
        log.info("Get /course/{}/edit - showing edit course page.", id);
        model.addAttribute("courseDto", courseService.getCourseById(id));
        return "edit-course";
    }

    // ===== NEW: save the edited course =====
    @PostMapping("/{id}/edit")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("Post /course/{}/edit - update course request received.", id);
        courseDTO.setId(id); // keep the id so the form action still works if we re-show the page

        if (bindingResult.hasErrors()) {
            log.error("Post /course/{}/edit - validation error.", id);
            return "edit-course";
        }

        if (courseService.existsByCourseCodeAndIdNot(courseDTO.getCourseCode(), id)) {
            log.error("Post /course/{}/edit - code must be unique.", id);
            bindingResult.rejectValue("courseCode", null, "Code must be unique");
            return "edit-course";
        }

        courseService.updateCourse(id, courseDTO);
        redirectAttributes.addFlashAttribute("message", "Course updated successfully");
        return "redirect:/course/list";
    }

}