package com.example.bills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * dto счета.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDto {
    /**
     * Идентификатор
     */
    private Long id;

    /**
     * Текущая сумма.
     */
    private BigDecimal currentSum;

    /**
     * Название.
     */
    private String name;
}
