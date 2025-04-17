package com.kaif.springboot.windsorbookshop.dto;

public class SummaryDTO {

    private int users;
    private int books;
    private int orders;

    public SummaryDTO(int users, int books, int orders) {
        this.users = users;
        this.books = books;
        this.orders = orders;
    }

    // Getters and Setters
    public int getUsers() {
        return users;
    }

    public void setUsers(int users) {
        this.users = users;
    }

    public int getBooks() {
        return books;
    }

    public void setBooks(int books) {
        this.books = books;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }
}
