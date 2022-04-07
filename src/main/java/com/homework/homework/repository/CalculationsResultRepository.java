package com.homework.homework.repository;

import com.homework.homework.model.CalculationsResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CalculationsResultRepository extends JpaRepository<CalculationsResult, Long> {

    @Query(value = "SELECT value FROM constants WHERE key = 'bankMargin'", nativeQuery = true)
    Double loadBankMargin();
}
