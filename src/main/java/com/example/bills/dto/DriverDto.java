package com.example.bills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * dto водителя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDto {
    /**
     * Идентификатор.
     */
    private Long id;
    /**
     * ФИО.
     */
    private String fio;
    /**
     * Счета.
     */
    private List<BillDto> bills;
}