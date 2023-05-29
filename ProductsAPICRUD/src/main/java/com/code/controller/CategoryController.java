package com.code.controller;


import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.model.Category;
import com.code.model.Product;
import com.code.repository.CategoryRepository;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;
	
	@PostMapping("/categories") 
	public Category addNewCategory(@RequestBody Category category){
		categoryRepository.save(category);
		return category;	
	}
	
	@GetMapping("/categories") 
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categoryList = new ArrayList<>();
		categoryRepository.findAll().forEach(categoryList::add);
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	@GetMapping("/categories/{categoryid}")
	public ResponseEntity<Category> getCategoryById(@PathVariable long categoryid) {
		Optional<Category> category = categoryRepository.findById(categoryid);
		
		if(category.isPresent()) {
			return new ResponseEntity<Category>(category.get(), HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Category>(category.get(),HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/categories/{categoryid}")
	public String updateCategoryById(@PathVariable long categoryid, @RequestBody Category category) {
		Optional<Category> cat = categoryRepository.findById(categoryid);
		if(cat.isPresent()) 
		{
			Category existCategory = cat.get();
			existCategory.setCategory_name(category.getCategory_name());		
			categoryRepository.save(existCategory);
			return "Category Details Against Id " + categoryid + " udpated";
		}
		else {
			return "Category Details does not exist for categoryid " + categoryid;
		}
	}
	
	@DeleteMapping("/categories/{categoryid}")
	public String deleteCategoryById(@PathVariable long categoryid) {
			categoryRepository.deleteById(categoryid);
			return "Category deletetd successfully";
	}
}
