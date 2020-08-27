package com.example.bills.services;

import com.example.bills.domain.Bill;
import com.example.bills.dto.KreditDebitDto;
import com.example.bills.dto.SumTransferDto;

import java.util.List;

public interface BillService {
  void kredit(KreditDebitDto dto);

  void debit(KreditDebitDto dto);

  void transfer(SumTransferDto dto);
}
