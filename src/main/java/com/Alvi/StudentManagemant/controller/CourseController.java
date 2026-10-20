package com.Alvi.StudentManagemant.controller;


import com.Alvi.StudentManagemant.dto.CourseDTO;
import com.Alvi.StudentManagemant.repository.CourseRepository;
import com.Alvi.StudentManagemant.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    CourseController(CourseService courseService){
    this.courseService = courseService;

}

    @GetMapping("/new")
    public String showCreateCourse(Model model){

        model.addAttribute("courseDto", new CourseDTO());
    return "add-course";
}

    @GetMapping("/list")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }

    @PostMapping
    public String createCourses (@Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "add-course";
        }

        if (courseService.existsByCourseCode(courseDTO.getCourseCode())) {


        bindingResult.rejectValue("courseCode", null, "Code must be unique");
        return "add-course";
    }

        courseService.createCourse(courseDTO);
        redirectAttributes.addFlashAttribute("message", "Course created successfully ");

            return "redirect:/course/list";

    }
}
