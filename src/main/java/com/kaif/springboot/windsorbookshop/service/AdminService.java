package com.kaif.springboot.windsorbookshop.service;

import com.kaif.springboot.windsorbookshop.repo.BookRepo;
import com.kaif.springboot.windsorbookshop.repo.OrderRepository;
import com.kaif.springboot.windsorbookshop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepo bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Method to get the total number of users
    public int getTotalUsers() {
        return (int) userRepository.count();  // Assuming you have a UserRepository with a count() method
    }

    // Method to get the total number of books
    public int getTotalBooks() {
        return (int) bookRepository.count();  // Assuming you have a BookRepository with a count() method
    }

    // Method to get the total number of orders
    public int getTotalOrders() {
        return (int) orderRepository.count();  // Assuming you have an OrderRepository with a count() method
    }
}
