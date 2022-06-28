package com.company.goodreads.dao.repository;

import com.company.goodreads.dao.entity.BookReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewsRepository extends JpaRepository<BookReviewsEntity,Long> {
}
