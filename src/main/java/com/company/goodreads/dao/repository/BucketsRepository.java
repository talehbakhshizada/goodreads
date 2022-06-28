package com.company.goodreads.dao.repository;

import com.company.goodreads.dao.entity.BucketsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BucketsRepository extends JpaRepository<BucketsEntity,Long> {
    Optional<BucketsEntity> findByUserId(Long id);
}
