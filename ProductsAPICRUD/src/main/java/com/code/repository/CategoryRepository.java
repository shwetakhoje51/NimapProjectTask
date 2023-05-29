package com.code.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.code.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
}