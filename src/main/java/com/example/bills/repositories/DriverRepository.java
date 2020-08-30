package com.example.bills.repositories;

import com.example.bills.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Репозиторий водителей.
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    /**
     * Получить текущую сумму.
     *
     * @param id     id водителя
     * @param billId id счета
     * @return Текущая сумма
     */
    @Query(
            "select b.currentSum from  Driver d left join Bill b on d.id=d.id where d.id = :id and b.id = :billId")
    Optional<BigDecimal> getCurrentSumByIdAndBillId(
            @Param(value = "id") Long id, @Param(value = "billId") Long billId);
}
