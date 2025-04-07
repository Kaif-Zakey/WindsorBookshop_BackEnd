package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.entitis.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByOrderId(Long orderId);
}
