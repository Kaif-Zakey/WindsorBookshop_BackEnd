package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.entitis.Books;
import com.kaif.springboot.windsorbookshop.entitis.Cart;
import com.kaif.springboot.windsorbookshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Repository

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(User user);

    Cart findByUserAndBook(User user, Books book);

    Optional<Cart> findByUserIdAndBookId(int userId, int bookId);

    @Transactional
    void deleteByUserId(int userId);

    List<Cart> findByUser_Id(int userId);

    void deleteByUser_Id(int userId);

    List<Cart> findByUserId(int userId);

    Optional<Double> getCartTotalByUserId(int userId);
}
