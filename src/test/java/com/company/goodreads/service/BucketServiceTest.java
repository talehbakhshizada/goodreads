package com.company.goodreads.service;

import com.company.goodreads.dao.entity.BooksEntity;
import com.company.goodreads.dao.entity.BucketsEntity;
import com.company.goodreads.dao.entity.UsersEntity;
import com.company.goodreads.dao.repository.BooksRepository;
import com.company.goodreads.dao.repository.BucketsRepository;
import com.company.goodreads.dao.repository.UsersRepository;
import com.company.goodreads.model.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BucketServiceTest {

    @Mock
    UsersRepository usersRepository;
    @Mock
    BucketsRepository bucketsRepository;
    @Mock
    BooksRepository booksRepository;

    @InjectMocks
    BucketService bucketService;

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach setUp method");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteBookFromBucketUserRepositoryException() {
        Optional<UsersEntity> user = Optional.empty();
        Mockito.when(usersRepository.findByLogin("test")).thenReturn(user);

        Assertions.assertThrows(
                NotFoundException.class,
                () -> bucketService.deleteBookFromBucket("test", 1L)
        );
    }

    @Test
    void deleteBookFromBucketSuccess() {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(1L);
        Optional<UsersEntity> user = Optional.of(usersEntity);

        Mockito.when(usersRepository.findByLogin("test")).thenReturn(user);

        BucketsEntity bucketsEntity = new BucketsEntity();
        Optional<BucketsEntity> bucket = Optional.of(bucketsEntity);
        Mockito.when(bucketsRepository.findByUserId(1L)).thenReturn(bucket);

        BooksEntity booksEntity = new BooksEntity();
        Optional<BooksEntity> book = Optional.of(booksEntity);

        Mockito.when(booksRepository.findById(5L)).thenReturn(book);

        bucketService.deleteBookFromBucket("test", 5L);
    }
}