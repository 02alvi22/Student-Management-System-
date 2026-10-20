package com.Alvi.StudentManagemant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CourseDTO {

    private Long id;

    @NotBlank(message = "Course name is required.")
    @Size(max = 150, message = "Maximum of 150 characters is allowed")
    private String courseName;

    @NotBlank(message = "Course code is required.")
    private String courseCode;

    @NotBlank(message = "Course duration is required.") // CHANGED: message fixed
    private String duration;

    private boolean active; // CHANGED: removed @NotBlank (it can't validate a boolean)

    @NotNull(message = "Course fee is required.") // CHANGED: @NotNull instead of @NotBlank
    private BigDecimal fee;

    @Size(max = 500, message = "Maximum of 500 characters is allowed")
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public BigDecimal getFee() { return fee; }
    public void setFee(BigDecimal fee) { this.fee = fee; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}