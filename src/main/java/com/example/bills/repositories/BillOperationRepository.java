package com.example.bills.repositories;

import com.example.bills.domain.BillOperation;
import com.example.bills.enums.BillOperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BillOperationRepository extends JpaRepository<BillOperation, Long> {

  Page<BillOperation> findAllByDateBetween(Date start, Date end, Pageable pageable);

  List<BillOperation> findAllByBillIdAndTypeAndDateBetween(
      Long billId, BillOperationType type, Date start, Date end);
}
