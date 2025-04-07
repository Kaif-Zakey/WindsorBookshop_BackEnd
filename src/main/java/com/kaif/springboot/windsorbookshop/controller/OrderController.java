package com.kaif.springboot.windsorbookshop.controller;

import com.kaif.springboot.windsorbookshop.dto.OrderDTO;
import com.kaif.springboot.windsorbookshop.entitis.Order;
import com.kaif.springboot.windsorbookshop.service.OrderService;
import com.kaif.springboot.windsorbookshop.user.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 📌 STAFF ONLY: Get all orders
    @GetMapping("all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(orders);
    }

    // 📌 Customer Checkout
    @PostMapping("/checkout/{userId}")
    public ResponseEntity<?> checkout(@PathVariable int userId) {
        try {
            Order order = orderService.checkout(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Unable to checkout. " + e.getMessage());
        }
    }

    // 📌 Get all orders for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable int userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for this user.");
        }
        return ResponseEntity.ok(orders);
    }

    // 📌 Get order history for a user
    @GetMapping("/history/{userId}")
    public ResponseEntity<?> getUserOrdersForHistory(@PathVariable int userId) {
        List<Order> orders = orderService.getUserOrdersForHistory(userId);

        if (orders.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // ✅ Empty JSON array
        }

        return ResponseEntity.ok(orders);
    }

    // 📌 STAFF ONLY: Update order status (PENDING -> SHIPPED or CANCELLED)
    @PutMapping("/{orderId}/status/{newStatus}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable int orderId, @PathVariable String newStatus) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 📌 CUSTOMER: View order status
    @GetMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> getOrderStatus(@PathVariable int orderId) {
        try {
            OrderDTO status = orderService.getOrderStatus(orderId);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalOrders() {
        long count = orderService.getTotalOrders();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/pending")
    public ResponseEntity<Long> getPendingOrdersCount() {
        long count = orderService.getPendingOrdersCount();
        return ResponseEntity.ok(count);
    }


    // 📌 View Order Details by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable int orderId) {
        try {
            OrderDTO order = orderService.getOrderDetails(orderId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // ✅ DELETE Order (Only if Status is "Pending" or "Cancelled")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable int orderId) {
        boolean isDeleted = orderService.deleteOrder(orderId);

        if (isDeleted) {
            return ResponseEntity.ok("Order deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Order cannot be deleted.");
        }
    }


}
