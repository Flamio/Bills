package com.example.bills.services;

import com.example.bills.dto.BillHistoryDto;
import com.example.bills.dto.BillTurnoverDto;
import com.example.bills.dto.KreditDebitDto;
import com.example.bills.dto.PeriodQueryDto;
import com.example.bills.dto.SumTransferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {
  void kredit(KreditDebitDto dto);

  void debit(KreditDebitDto dto);

  void transfer(SumTransferDto dto);

  BillTurnoverDto turnover(PeriodQueryDto dto);

  Page<BillHistoryDto> history(PeriodQueryDto dto, Pageable pageable);
}
