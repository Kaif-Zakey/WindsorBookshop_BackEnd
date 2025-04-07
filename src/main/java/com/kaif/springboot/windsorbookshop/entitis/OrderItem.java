package com.kaif.springboot.windsorbookshop.entitis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kaif.springboot.windsorbookshop.entitis.*;
import com.kaif.springboot.windsorbookshop.entitis.Books;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore  // Prevents infinite recursion
    private Order order;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Books book;

    private int quantity;
    private double totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderItem() {
    }

    public OrderItem(int id, Order order, Books book, int quantity, double totalPrice) {
        this.id = id;
        this.order = order;
        this.book = book;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
