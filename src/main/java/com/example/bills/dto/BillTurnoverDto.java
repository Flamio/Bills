package com.example.bills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillTurnoverDto {
  private BigDecimal debit;
  private BigDecimal сredit;
}
