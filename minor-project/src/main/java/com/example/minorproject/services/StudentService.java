package com.example.minorproject.services;

import com.example.minorproject.dto.StudentCreateRequest;
import com.example.minorproject.models.Student;
import com.example.minorproject.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public void create(StudentCreateRequest studentCreateRequest){
        Student student = studentCreateRequest.to();
        studentRepository.save(student);
    }

    public Student get(Integer id) {
        return this.studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
    }
}
