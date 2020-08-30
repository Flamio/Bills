package com.example.bills.repositories;

import com.example.bills.domain.BillOperation;
import com.example.bills.enums.BillOperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Репозиторий операций по счету.
 */
public interface BillOperationRepository extends JpaRepository<BillOperation, Long> {

    /**
     * Найти по датам.
     *
     * @param start    начальная дата
     * @param end      конечная дата
     * @param pageable запрос страницы
     * @return страница истории по операциям
     */
    Page<BillOperation> findAllByDateBetween(Date start, Date end, Pageable pageable);

    /**
     * Найти по датам, типу операции и id счета.
     *
     * @param billId id счета
     * @param type   тип операции
     * @param start  начальная дата
     * @param end    конечная дата
     * @return Операции по счету.
     */
    List<BillOperation> findAllByBillIdAndTypeAndDateBetween(
            Long billId, BillOperationType type, Date start, Date end);
}
