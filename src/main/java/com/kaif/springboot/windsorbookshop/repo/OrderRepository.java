package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.dto.OrderDTO;
import com.kaif.springboot.windsorbookshop.entitis.Order;
import com.kaif.springboot.windsorbookshop.user.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_Id(int userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findByUserId(@Param("userId") int userId);

    long countByStatus(OrderStatus status);


//    List<Order> findByStatus(OrderStatus status);


}
