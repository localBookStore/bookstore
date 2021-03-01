package com.webservice.bookstore.domain.entity.category;

import com.webservice.bookstore.domain.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
