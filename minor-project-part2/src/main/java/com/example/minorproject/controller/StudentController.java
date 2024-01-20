package com.example.minorproject.controller;


import com.example.minorproject.dto.StudentCreateRequest;
import com.example.minorproject.models.Student;
import com.example.minorproject.models.User;
import com.example.minorproject.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

    // POJO - plain old java object

    private static Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    @PostMapping("")
    public void createStudent(@Valid @RequestBody StudentCreateRequest request){

        logger.info("request - {}", request);
        studentService.create(request);

//        StudentCreateRequest studentCreateRequest = new StudentCreateRequest();
//        studentCreateRequest.setAge(10);
//        studentCreateRequest.setName("10");
//        studentCreateRequest.setRollNo("10");
//        studentCreateRequest.setEmail("10");

//        StudentCreateRequest studentCreateRequest = new StudentCreateRequest
//                ("ABC", null, 20, null, null);
//
//        StudentCreateRequest.builder()
//                .name("ABC")
//                .age(30)
//                .build();

    }



    // student --> his / her details only
    // admin --> every student's details

    // Student only
    @GetMapping("")
    public Student getStudent(){
        // id - from token / authentication token {student id}
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        return this.studentService.get(user.getStudent().getId());
    }

    // Admin only
    @GetMapping("/by-admin")
    public Student getStudent(@RequestParam("id") Integer studentId){
        // id - from token / authentication token {admin id}
        return this.studentService.get(studentId);
    }
}
