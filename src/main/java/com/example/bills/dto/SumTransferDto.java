package com.example.bills.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * dto для запроса перевода с счета на счет.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SumTransferDto extends KreditDebitDto {

    /**
     * Счет, на который переведутся средства.
     */
    @NonNull
    private Long destinationBillId;
}
