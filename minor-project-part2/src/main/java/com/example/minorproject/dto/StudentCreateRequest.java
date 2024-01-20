package com.example.minorproject.dto;

import com.example.minorproject.models.Student;
import com.example.minorproject.models.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StudentCreateRequest {

    @NotBlank
    private String name;
    @Email
    private String email;

    @Min(18)
    private Integer age;

    @NotBlank
    private String rollNo;
    private String mobile;

    @NotBlank
    private String username;

    @NotBlank
    private String password; // raw form


    public Student to(){
        return Student.builder()
                .name(this.name)
                .age(this.age)
                .roll_number(this.rollNo)
                .mobile(this.mobile)
                .email(this.email)
                .cardId(UUID.randomUUID().toString())
                .user(
                        User.builder()
                                .username(this.username)
                                .password(this.password)
                                .build()
                )
                .build();
    }
}
