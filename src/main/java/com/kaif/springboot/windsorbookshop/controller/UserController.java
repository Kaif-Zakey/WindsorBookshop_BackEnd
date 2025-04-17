package com.kaif.springboot.windsorbookshop.controller;


import com.kaif.springboot.windsorbookshop.service.UserService;
import com.kaif.springboot.windsorbookshop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }



}
