package com.risknarrative.repository;

import com.risknarrative.entity.Officers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OfficerRepository extends JpaRepository<Officers, Long> {
}
