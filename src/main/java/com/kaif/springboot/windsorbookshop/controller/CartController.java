package com.kaif.springboot.windsorbookshop.controller;

import com.kaif.springboot.windsorbookshop.entitis.Cart;
import com.kaif.springboot.windsorbookshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Add book to cart
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam int userId, @RequestParam int bookId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addToCart(userId, bookId, quantity));
    }

    // Get user's cart items
    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getUserCart(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

    // Update cart quantity
    @PutMapping("/{cartItemId}/update")
    public ResponseEntity<Cart> updateCart(@PathVariable int cartItemId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateCartQuantity(cartItemId, quantity));
    }

    // Remove item from cart
    @DeleteMapping("/{cartItemId}/remove")
    public ResponseEntity<String> removeCartItem(@PathVariable int cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok("Item removed from cart");
    }

    // Clear entire cart
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable int userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }

    // Get total price of the cart
    @GetMapping("/{userId}/total")
    public ResponseEntity<Double> getCartTotal(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getCartTotal(userId));
    }

    @GetMapping("total/{userId}")
    public ResponseEntity<Map<String, Double>> getCartTotal1(@PathVariable int userId) {
        double total = cartService.getCartTotalForCheckout(userId);
        Map<String, Double> response = new HashMap<>();
        response.put("totalPrice", total);
        return ResponseEntity.ok(response);
    }

}
