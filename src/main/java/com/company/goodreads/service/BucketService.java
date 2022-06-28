package com.company.goodreads.service;

import com.company.goodreads.dao.entity.BooksEntity;
import com.company.goodreads.dao.entity.BucketsEntity;
import com.company.goodreads.dao.entity.UsersEntity;
import com.company.goodreads.dao.repository.BooksRepository;
import com.company.goodreads.dao.repository.BucketsRepository;
import com.company.goodreads.dao.repository.UsersRepository;
import com.company.goodreads.model.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BucketService {

    private BucketsRepository bucketsRepository;
    private UsersRepository usersRepository;
    private BooksRepository booksRepository;

    public BucketService(UsersRepository usersRepository,
                         BucketsRepository bucketsRepository, BooksRepository booksRepository) {
        this.usersRepository = usersRepository;
        this.bucketsRepository = bucketsRepository;
        this.booksRepository = booksRepository;
    }


    public BucketsEntity getBucket(String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        });

        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElse(new BucketsEntity());
        return bucket;
    }

    public void addToBucket(String login, Long bookId) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        });
        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElse(new BucketsEntity());
        BooksEntity book = booksRepository.findById(bookId)
                .orElseThrow(() -> {
                    throw new NotFoundException("Book not found");
                });
        bucket.setUserId(user.getId()); // eger bucket bazada tapilmasa yeni bos bucketin idsine set edirik userin idsini
        List<BooksEntity> books = bucket.getBooks();
        books.add(book);
        bucket.setBooks(books);

        bucketsRepository.save(bucket);
    }

    public void deleteBookFromBucket(String login, Long bookId) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(() -> {
                    throw new NotFoundException("User not found");
                });
        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElseThrow(() -> {
                    throw new NotFoundException("Bucket not found");
                });
        BooksEntity book = booksRepository.findById(bookId)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("Book not found");
                        });

        List<BooksEntity> books = bucket.getBooks();
        books.remove(book);
        bucket.setBooks(books);

        bucketsRepository.save(bucket);
    }

}

