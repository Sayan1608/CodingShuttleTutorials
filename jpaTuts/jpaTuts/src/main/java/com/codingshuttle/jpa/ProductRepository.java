package com.codingshuttle.jpa;

import com.codingshuttle.jpa.entities.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByTitle(String title);
    Boolean existsByTitle(String title);
    List<ProductEntity> findByTitleIsLike(String title);
    List<ProductEntity> findByCreatedOnBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    @Query("Select e from ProductEntity e where title=:title and price=:price")
    ProductEntity findByTitleAndPrice(String title, String price);

    // sorting & pagination in Spring Boot :

//    List<ProductEntity> findAllByOrderByTitle();
    List<ProductEntity> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<ProductEntity> findBy(Sort sort);






}
