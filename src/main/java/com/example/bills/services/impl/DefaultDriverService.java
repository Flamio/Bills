package com.example.bills.services.impl;

import com.example.bills.domain.Bill;
import com.example.bills.domain.Driver;
import com.example.bills.dto.DriverDto;
import com.example.bills.mappers.DriverMapper;
import com.example.bills.repositories.DriverRepository;
import com.example.bills.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DefaultDriverService implements DriverService {

  private final DriverRepository driverRepository;
  private final DriverMapper driverMapper;

  @Override
  @Transactional
  public List<DriverDto> list() {
    final List<Driver> drivers = driverRepository.findAll();
    return drivers.stream()
        .map(driverMapper::toDriverDto)
        .collect(Collectors.toList());
  }

  @Override
  public List<Bill> getBills(final Long driverId) {
    Optional<Driver> driverById = driverRepository.findById(driverId);
    if (driverById.isEmpty()) return new ArrayList<>();

    final Driver driver = driverById.get();
    return driver.getBills();
  }
}
