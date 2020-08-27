package com.example.bills.services;

import com.example.bills.domain.Bill;
import com.example.bills.domain.Driver;
import com.example.bills.dto.DriverDto;

import java.util.List;

public interface DriverService {
    List<DriverDto> list();

    List<Bill> getBills(Long driverId);
}
