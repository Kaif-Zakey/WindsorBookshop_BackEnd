package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.entitis.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByUserId(Integer userId);
}
