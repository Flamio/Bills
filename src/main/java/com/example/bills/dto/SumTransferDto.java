package com.example.bills.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SumTransferDto extends KreditDebitDto {
  @NonNull private Long destinationBillId;
}
