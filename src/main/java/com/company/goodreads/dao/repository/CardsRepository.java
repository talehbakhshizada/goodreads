package com.company.goodreads.dao.repository;

import com.company.goodreads.dao.entity.CardsEntity;
import com.company.goodreads.dao.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardsRepository extends JpaRepository<CardsEntity,Long> {
    Optional<CardsEntity> findByNumber(String number);

    List<CardsEntity> findAllByUsersIn(List<UsersEntity> users);
}
