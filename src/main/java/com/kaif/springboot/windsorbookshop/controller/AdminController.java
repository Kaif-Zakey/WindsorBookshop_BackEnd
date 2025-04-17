package com.kaif.springboot.windsorbookshop.controller;

import com.kaif.springboot.windsorbookshop.dto.SummaryDTO;
import com.kaif.springboot.windsorbookshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/summary")
    public SummaryDTO getAdminSummary() {
        int totalUsers = adminService.getTotalUsers();
        int totalBooks = adminService.getTotalBooks();
        int totalOrders = adminService.getTotalOrders();
        
        return new SummaryDTO(totalUsers, totalBooks, totalOrders);
    }
}
