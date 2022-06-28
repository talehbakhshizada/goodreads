package com.company.goodreads.dao.repository;

import com.company.goodreads.dao.entity.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RolesEntity,Long> {
    RolesEntity findByRole(String role);
}
