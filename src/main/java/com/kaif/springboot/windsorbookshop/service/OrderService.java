package com.kaif.springboot.windsorbookshop.service;

import com.kaif.springboot.windsorbookshop.dto.OrderDTO;
import com.kaif.springboot.windsorbookshop.dto.ReviewDTO;
import com.kaif.springboot.windsorbookshop.entitis.*;
import com.kaif.springboot.windsorbookshop.repo.*;
import com.kaif.springboot.windsorbookshop.repo.UserRepository;
import com.kaif.springboot.windsorbookshop.user.OrderStatus;
import com.kaif.springboot.windsorbookshop.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private BookRepo booksRepository;

    @Transactional
    public Order checkout(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> cartItems = cartRepository.findByUser_Id(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalPrice = cartItems.stream()
                .mapToDouble(cart -> cart.getQuantity() * cart.getBook().getPrice())
                .sum();

        Order order = new Order();
        order.setUser(user);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING); // ✅ Default status is "PENDING"

        order = orderRepository.save(order);

        Order finalOrder = order;

        List<OrderItem> orderItems = cartItems.stream().map(cart -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(finalOrder);  // ✅ No more error
            orderItem.setBook(cart.getBook());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(cart.getQuantity() * cart.getBook().getPrice());

            // ✅ Reduce stock quantity
            Books book = cart.getBook();
            if (book.getQuantity() < cart.getQuantity()) {
                throw new RuntimeException("Not enough stock for book: " + book.getTitle());
            }
            book.setQuantity(book.getQuantity() - cart.getQuantity());
            booksRepository.save(book);

            return orderItem;
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        cartRepository.deleteByUser_Id(userId);

        return order;
    }

    public List<Order> getUserOrders(int userId) {
        return orderRepository.findByUser_Id(userId);
    }

    public List<Order> getUserOrdersForHistory(int userId) {
        return orderRepository.findByUser_Id(userId);
    }

    public Order updateOrderStatus(int orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            order.setStatus(OrderStatus.valueOf(newStatus.toUpperCase())); // ✅ Convert string to ENUM
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status. Allowed: PENDING, SHIPPED, CANCELLED.");
        }

        return orderRepository.save(order);
    }

    // 📌 Get all orders (For Staff)
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderDTO::new) // Convert to DTO
                .collect(Collectors.toList());    }

    public OrderDTO getOrderStatus(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderDTO(order);
    }

    public OrderDTO getOrderDetails(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return new OrderDTO(order);
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public long getPendingOrdersCount() {
        return orderRepository.countByStatus(OrderStatus.PENDING);
    }


    public boolean deleteOrder(int orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();

            // Check the status directly using the Enum values
            if (order.getStatus() == OrderStatus.PENDING || order.getStatus() == OrderStatus.CANCELLED) {
                orderRepository.deleteById(orderId);
                return true;
            }
        }

        return false;
    }


}
