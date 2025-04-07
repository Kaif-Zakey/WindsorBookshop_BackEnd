package com.kaif.springboot.windsorbookshop.repo;

import com.kaif.springboot.windsorbookshop.entitis.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
