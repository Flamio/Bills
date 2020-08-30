package com.example.bills.controllers;

import com.example.bills.dto.DriverDto;
import com.example.bills.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Контроллер для работы с водителями.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/drivers")
public class DriversController {

    /**
     * Сервис для работы с водителями.
     */
    private final DriverService driverService;

    /**
     * Получить список водителей.
     *
     * @return Список водителей.
     */
    @GetMapping("/list")
    public List<DriverDto> list() {
        return driverService.list();
    }

    /**
     * Получить текущую сумму на счете.
     *
     * @param id     Идентификатор водителя.
     * @param billId Идентификатор счета.
     * @return Текущая сумма по счету.
     */
    @GetMapping("/{id}/bills/{billId}")
    public BigDecimal getCurrentSum(@PathVariable Long id, @PathVariable Long billId) {
        return driverService.currentSumInBill(id, billId);
    }
}
