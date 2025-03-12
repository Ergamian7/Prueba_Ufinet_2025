package com.managment.api.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managment.api.user.entity.User;
import com.managment.api.user.request.LoginRequest;
import com.managment.api.user.response.LoginResponse;
import com.managment.api.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/email/{email}")
    public String verifyEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            return "The email is already registered!";
        }
        return "OK";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userService.add(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user != null && userService.checkPassword(user,
                loginRequest.getPassword())) {
            return userService.getTokenByUsername(user.getEmail());
        }
        return LoginResponse.builder()
                .message("Username or password invalid!")
                .status(false)
                .build();
    }

}

