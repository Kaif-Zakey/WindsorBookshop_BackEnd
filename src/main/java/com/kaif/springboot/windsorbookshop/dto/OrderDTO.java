package com.kaif.springboot.windsorbookshop.dto;

import com.kaif.springboot.windsorbookshop.entitis.Order;
import com.kaif.springboot.windsorbookshop.user.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private int id;
    private String customerName;
    private String bookTitle;
    private Integer quantity;
    private Double totalPrice;
    private OrderStatus status;

    // Constructor
    public OrderDTO(Order order) {
        this.id = order.getId();
        this.customerName = order.getUser().getFirstname();
        this.bookTitle = order.getOrderItems().get(0).getBook().getTitle();
        this.quantity = order.getOrderItems().get(0).getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
    }


}
