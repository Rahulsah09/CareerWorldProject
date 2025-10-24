package com.career_guidance.controller;


import com.career_guidance.model.User;
import com.career_guidance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
