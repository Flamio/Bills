package com.example.bills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * dto для вывода единицы истории об операциях по счету.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillHistoryDto {
    /**
     * Сумма.
     */
    private BigDecimal sum;

    /**
     * Дата.
     */
    private Date date;

    /**
     * Тип операции.
     */
    private String type;

    /**
     * Детали.
     */
    private String details;
}
