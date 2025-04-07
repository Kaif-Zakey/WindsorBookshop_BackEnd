package com.kaif.springboot.windsorbookshop.user;

public enum OrderStatus {
    PENDING,    // Default status when order is placed
    SHIPPED,    // Order is shipped
    CANCELLED   // Order is cancelled by staff or user
}
