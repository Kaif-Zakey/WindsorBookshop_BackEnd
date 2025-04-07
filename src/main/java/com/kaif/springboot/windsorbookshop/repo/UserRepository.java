package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
   Optional<User> findByEmail(String email);
   void deleteByEmail(String email);

}
