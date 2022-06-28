package com.company.goodreads.dao.repository;

import com.company.goodreads.dao.entity.BooksEntity;
import com.company.goodreads.model.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<BooksEntity,Long> {
    List<BooksEntity> findAllByStatus(BookStatus status);
}
