package com.example.dtos;


import com.example.models.User;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    private String name;

    @NotEmpty
    private String contact;

    private String email;

    @Min(18)
    private Integer age;

    public User to() {

        return User.builder()
                .name(this.name)
                .contact(this.contact)
                .age(this.age)
                .email(this.email)
                .build();
    }
}
