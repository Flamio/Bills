package com.example.bills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * dto для оборота по счету.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillTurnoverDto {
    /**
     * Дебет.
     */
    private BigDecimal debit;

    /**
     * Кредит.
     */
    private BigDecimal сredit;
}
