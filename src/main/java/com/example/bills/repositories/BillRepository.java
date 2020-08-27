package com.example.bills.repositories;

import com.example.bills.domain.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
  boolean existsByIdAndAndDriverId(Long id, Long driverId);

  List<Bill> findAllByDriverId(Long driverId);
}
