package com.example.bills.services;

import com.example.bills.domain.Bill;
import com.example.bills.dto.DriverDto;

import java.math.BigDecimal;
import java.util.List;

public interface DriverService {
  List<DriverDto> list();

  BigDecimal currentSumInBill(Long id, Long bill);
}
