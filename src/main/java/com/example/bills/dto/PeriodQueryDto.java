package com.example.bills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * dto для запроса периода операций.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeriodQueryDto {
    /**
     * id счета.
     */
    private Long billId;
    /**
     * id водителя.
     */
    private Long driverId;

    /**
     * Дата начала.
     */
    private Date startDate;

    /**
     * Дата конца.
     */
    private Date endDate;
}
