package com.example.bills.services.impl;

import com.example.bills.domain.Driver;
import com.example.bills.dto.DriverDto;
import com.example.bills.exceptions.BillNotFoundException;
import com.example.bills.mappers.DriverMapper;
import com.example.bills.repositories.DriverRepository;
import com.example.bills.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultDriverService implements DriverService {

  private final DriverRepository driverRepository;
  private final DriverMapper driverMapper;

  @Override
  public List<DriverDto> list() {
    final List<Driver> drivers = driverRepository.findAll();
    return drivers.stream().map(driverMapper::toDriverDto).collect(Collectors.toList());
  }

  @Override
  public BigDecimal currentSumInBill(final Long id, final Long billId) {
    Optional<BigDecimal> currentSumByIdAndBillId =
        driverRepository.getCurrentSumByIdAndBillId(id, billId);
    if (currentSumByIdAndBillId.isEmpty()) throw new BillNotFoundException("bill not found");
    return currentSumByIdAndBillId.get();
  }
}
