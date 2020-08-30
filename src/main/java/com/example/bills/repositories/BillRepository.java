package com.example.bills.repositories;

import com.example.bills.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий счетов.
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    /**
     * Существует ли счет у водителя
     *
     * @param id       id счета
     * @param driverId id водителя
     * @return признак существования
     */
    boolean existsByIdAndAndDriverId(Long id, Long driverId);
}
