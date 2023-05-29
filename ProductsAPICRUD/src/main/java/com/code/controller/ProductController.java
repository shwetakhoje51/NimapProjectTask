package com.code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.*;

import com.code.model.Category;
import com.code.model.Product;
import com.code.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@PostMapping("/products")
	public String addNewProduct(@RequestBody Product product) {
		productRepository.save(product);
		return "Product is added in database";
	}
				
	 @GetMapping("/products")
	    public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page,
	                                     @RequestParam(defaultValue = "5") int size) {
	        // Create a PageRequest object with the desired page number and page size
	        PageRequest pageRequest = PageRequest.of(page, size);

	        // Use the findAll() method of the repository with the PageRequest object
	        return productRepository.findAll(pageRequest);
	    }
	
	/*
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> productList = new ArrayList<>();
		productRepository.findAll().forEach(productList::add);
		return new ResponseEntity<List<Product>>(productList, HttpStatus.OK);
	}*/
	
	
	@GetMapping("/products/{productid}")
	public ResponseEntity<Product> getProductById(@PathVariable long productid) {
		Optional<Product> product = productRepository.findById(productid);
		if (product.isPresent()) {
			return new ResponseEntity<Product>(product.get(), HttpStatus.FOUND);
		}

		else {
			return new ResponseEntity<Product>(product.get(), HttpStatus.NOT_FOUND);
		}
	}
	
	/*
	@GetMapping("/products/{categoryid}") 
	public ResponseEntity<Product> getProductByCategory(@PathVariable long categoryid) {
		Optional<Product> product = productRepository.findById(categoryid);
		
		if(product.isPresent()) {
			return new ResponseEntity<Product>(product.get(), HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Product>(product.get(),HttpStatus.NOT_FOUND);
		}
	}*/
	

	@PutMapping("/products/{productid}")
	public String updateProductById(@PathVariable long productid, @RequestBody Product product) {
		Optional<Product> prod = productRepository.findById(productid);
		if (prod.isPresent()) {
			Product existProduct = prod.get();
			existProduct.setProduct_name(product.getProduct_name());
			existProduct.setProduct_desc(product.getProduct_desc());
			existProduct.setProduct_price(product.getProduct_price());
			productRepository.save(existProduct);
			return "Product Details against Id " + productid + " updated";
		} else {
			return "Product Details does not exist for productid " + productid;
		}

	}

	@DeleteMapping("/products/{productid}")
	public String deleteProductById(@PathVariable long productid) {
		productRepository.deleteById(productid);
		return "Product deleted successfully.";
	}

}
