package com.codingshuttle.jpa.controllers;

import com.codingshuttle.jpa.ProductRepository;
import com.codingshuttle.jpa.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final Integer PAGE_SIZE=5;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping(path = "/v1")
    public List<ProductEntity> getSortedProducts(
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction){
//        return productRepository.findAllByOrderByTitle();
        if (direction.toUpperCase().equals("DESC")) {
            return productRepository.findBy(Sort.by(Sort.Order.desc(sortBy), Sort.Order.asc("quantity")));
        }
        return productRepository.findBy(Sort.by(Sort.Order.asc(sortBy), Sort.Order.asc("quantity")));
    }

    @GetMapping(path = "/v2")
    public List<ProductEntity> getSortedAndPaginatedProducts(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction,
            @RequestParam(defaultValue = "0") Integer pageNumber){

        LocalDateTime startDate = LocalDateTime.of(2023,1,1,0,0,0);
        LocalDateTime endDate = LocalDateTime.of(2024,1,1,0,0,0);

        if(!"DESC".equalsIgnoreCase(direction)){
            direction = "ASC";
        }

        Pageable pageable = PageRequest.of(pageNumber,PAGE_SIZE,Sort.by(Sort.Direction.valueOf(direction), sortBy));
//
//        return productRepository.findAll(pageable);
//          return productRepository.findByTitleContainingIgnoreCase(title,pageable);
        return productRepository.findByCreatedOnBetween(startDate, endDate, pageable);
    }
}
