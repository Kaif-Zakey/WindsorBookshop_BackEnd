package com.kaif.springboot.windsorbookshop.service;

import com.kaif.springboot.windsorbookshop.entitis.Books;
import com.kaif.springboot.windsorbookshop.entitis.Cart;
import com.kaif.springboot.windsorbookshop.repo.BookRepo;
import com.kaif.springboot.windsorbookshop.repo.CartRepository;
import com.kaif.springboot.windsorbookshop.repo.UserRepository;
import com.kaif.springboot.windsorbookshop.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepo bookRepository;

    // Add book to cart
    public Cart addToCart(int userId, int bookId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        int availableStock = book.getQuantity();

        // Check if the user already has this book in their cart
        Cart cartItem = cartRepository.findByUserAndBook(user, book);

        int totalRequestedQty = (cartItem != null) ? cartItem.getQuantity() + quantity : quantity;

        // If requested quantity exceeds stock, throw an error
        if (totalRequestedQty > availableStock) {
            throw new RuntimeException("Out of stock, can't add to cart. Available stock: " + availableStock);
        }

        if (cartItem == null) {
            // No existing cart item found, create a new one
            cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setBook(book);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(book.getPrice() * quantity);
        } else {
            // Update the existing cart item
            if (availableStock >= quantity) {

                cartItem.setQuantity(totalRequestedQty);
                cartItem.setTotalPrice(cartItem.getQuantity() * book.getPrice());
            }else
            {
                throw new RuntimeException("Out of stock, can't add to cart. Available stock: " + availableStock);
            }
        }

        return cartRepository.save(cartItem);
    }

    // Get all cart items for a user
    public List<Cart> getUserCart(int userId) {
        return cartRepository.findByUserId(userId);
    }

    // Update quantity in cart
    public Cart updateCartQuantity(int cartItemId, int quantity) {
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Books book = cartItem.getBook();
        int availableStock = book.getQuantity();

        // ✅ If quantity is zero or negative, remove the cart item
        if (quantity <= 0) {
            cartRepository.delete(cartItem);
            return null;
        }

        // ✅ Stock validation: Prevent updating if quantity exceeds available stock
        if (quantity > availableStock) {
            throw new RuntimeException("Out of stock, can't update cart. Available stock: " + availableStock);
        }

        // ✅ Update the cart item with the new quantity
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(quantity * book.getPrice());

        return cartRepository.save(cartItem);
    }


    // Remove a specific item from cart
    public void removeCartItem(int cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    // Clear all cart items for a user
    public void clearCart(int userId) {
        cartRepository.deleteByUserId(userId);
    }

    // Get total cart price for a user
    public double getCartTotal(int userId) {
        return cartRepository.getCartTotalByUserId(userId).orElse(0.0);
    }

    public double getCartTotalForCheckout(int userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        return cartItems.stream()
                .mapToDouble(Cart::getTotalPrice) // Add up the total price of each item in the cart
                .sum();
    }

}
