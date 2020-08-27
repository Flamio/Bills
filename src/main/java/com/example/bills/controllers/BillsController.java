package com.example.bills.controllers;

import com.example.bills.dto.KreditDebitDto;
import com.example.bills.dto.SumTransferDto;
import com.example.bills.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bills")
public class BillsController {
  private final BillService billsService;

  @PostMapping("/kredit")
  public void kredit(@RequestBody KreditDebitDto dto) {
    billsService.kredit(dto);
  }

  @PostMapping("/debit")
  public void debit(@RequestBody KreditDebitDto dto) {
    billsService.debit(dto);
  }

  @PostMapping("/transfer")
  public void transfer(@RequestBody SumTransferDto dto) {
    billsService.transfer(dto);
  }
}
