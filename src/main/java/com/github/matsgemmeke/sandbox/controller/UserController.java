package com.github.matsgemmeke.sandbox.controller;

import com.github.matsgemmeke.sandbox.model.CompetitionEntry;
import com.github.matsgemmeke.sandbox.model.User;
import com.github.matsgemmeke.sandbox.model.UserDTO;
import com.github.matsgemmeke.sandbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    private List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    private UserDTO getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @PostMapping("/user")
    private User saveUser(@RequestBody User user) throws Exception {
        userService.saveUser(user);
        return user;
    }

    @DeleteMapping("/user/{id}")
    private int deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return id;
    }
}
