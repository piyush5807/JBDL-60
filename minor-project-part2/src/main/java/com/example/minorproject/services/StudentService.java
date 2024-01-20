package com.example.minorproject.services;

import com.example.minorproject.dto.StudentCreateRequest;
import com.example.minorproject.models.Student;
import com.example.minorproject.models.User;
import com.example.minorproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Component
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void create(StudentCreateRequest studentCreateRequest){
        Student student = studentCreateRequest.to();

        User user = student.getUser();
        user.setAuthorities("std");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);
        student.setUser(user);
        studentRepository.save(student);
    }

    public Student get(Integer id) {
        return this.studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
}
