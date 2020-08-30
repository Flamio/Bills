package com.example.bills.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

/**
 * dto для запроса списания/начисления на счет.
 */
@Data
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KreditDebitDto {
    /**
     * id водителя.
     */
    @NonNull
    private Long driverId;

    /**
     * id счета.
     */
    @NonNull
    private Long billId;

    /**
     * Сумма.
     */
    @NonNull
    private BigDecimal sum;
}
