package com.kaif.springboot.windsorbookshop.service;


import com.kaif.springboot.windsorbookshop.repo.UserRepository;
import com.kaif.springboot.windsorbookshop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
