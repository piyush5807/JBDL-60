package com.example.controllers;

import com.example.dtos.UserCreateRequest;
import com.example.models.User;
import com.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    public User createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
        return this.userService.create(userCreateRequest);
    }

    /**
     * Retrieval
     * 1. Cache - if present return from here
     * 2. DB - fetch from db and then put it in the cache for subsequent retrievals
     *
     * Feature req: whenever an account is created, we will add Rs 10 in the wallet {create a wallet and then add cash}
     */

    @GetMapping("")
    public User getUser(@RequestParam("id") Integer userId){
        return this.userService.get(userId);
    }

    // APIs should not be same
    // Method + Path
}
