package com.example.bills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/** dto для запроса списания/начисления на счет. */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KreditDebitDto {
  /** id водителя. */
  @NonNull private Long driverId;

  /** id счета. */
  @NonNull private Long billId;

  /** Сумма. */
  @NonNull private BigDecimal sum;
}
