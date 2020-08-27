package com.example.bills.controllers;

import com.example.bills.domain.Bill;
import com.example.bills.domain.Driver;
import com.example.bills.dto.DriverDto;
import com.example.bills.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class DriversController {
  private final DriverService driverService;

  @GetMapping("/list")
  public List<DriverDto> list() {
    return driverService.list();
  }

  @GetMapping("/{id}/bills/}")
  public List<Bill> getBills(@PathVariable Long id) {
    return driverService.getBills(id);
  }
}
