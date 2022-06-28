package com.company.goodreads.service;

import com.company.goodreads.dao.entity.*;
import com.company.goodreads.dao.repository.BucketsRepository;
import com.company.goodreads.dao.repository.CardsRepository;
import com.company.goodreads.dao.repository.OrdersRepository;
import com.company.goodreads.dao.repository.UsersRepository;
import com.company.goodreads.model.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private OrdersRepository ordersRepository;
    private UsersRepository usersRepository;
    private BucketsRepository bucketsRepository;
    private CardsRepository cardsRepository;

    public OrderService(OrdersRepository ordersRepository, UsersRepository usersRepository,
                        BucketsRepository bucketsRepository, CardsRepository cardsRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.bucketsRepository = bucketsRepository;
        this.cardsRepository = cardsRepository;
    }

    public List<OrdersEntity> getOrders(String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );

        List<OrdersEntity> orders = ordersRepository.findAllByUserId(user.getId());

        return orders;
    }

    public void createOrder(String login, OrdersEntity order, String cardNumber) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );

        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("Bucket not found");
                        }
                );

        List<BooksEntity> books = bucket.getBooks();
        BigDecimal amount = BigDecimal.ZERO;
        for (BooksEntity book : books) {
            amount = amount.add(book.getPrice()); // double olsaydi amount = amount + book.getPrice();Biddecimal methodlarnan isliyir
        }

        order.setTotalAmount(amount);
        order.setUserId(user.getId());
        order.setDate(LocalDateTime.now());
        CardsEntity card = cardsRepository.findByNumber(cardNumber)
                .orElseThrow(() -> {
                    throw new NotFoundException("Card not found");
                });
        order.setCard(card);

        ordersRepository.save(order);
    }

    public BigDecimal calculateOrderTotalAmount(String login) {
        UsersEntity user = usersRepository.findByLogin(login)
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("User not found");
                        }
                );

        BucketsEntity bucket = bucketsRepository.findByUserId(user.getId())
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("Bucket not found");
                        }
                );

        List<BooksEntity> books = bucket.getBooks();
        BigDecimal amount = BigDecimal.ZERO;
        for (BooksEntity book : books) {
            amount = amount.add(book.getPrice());
        }

        return amount;
    }
}
