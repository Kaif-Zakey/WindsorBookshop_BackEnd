package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.entitis.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Books, Integer> {
    List<Books> findByCategoryId(int categoryId);
    int countByCategoryId(int categoryId);


}
