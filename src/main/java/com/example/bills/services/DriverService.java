package com.example.bills.services;

import com.example.bills.dto.DriverDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Сервис для работы с водителями.
 */
public interface DriverService {
    /**
     * Получить список водителей.
     *
     * @return Список водителей.
     */
    List<DriverDto> list();


    /**
     * Получить текущую сумму на счете.
     *
     * @param id   Идентификатор водителя.
     * @param bill Идентификатор счета.
     * @return Текущая сумма по счету.
     */
    BigDecimal currentSumInBill(Long id, Long bill);
}
