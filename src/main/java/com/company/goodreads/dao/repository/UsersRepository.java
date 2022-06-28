package com.company.goodreads.dao.repository;

import com.company.goodreads.dao.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByLogin(String login);
}
