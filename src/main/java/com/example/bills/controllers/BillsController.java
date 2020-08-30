package com.example.bills.controllers;

import com.example.bills.dto.BillHistoryDto;
import com.example.bills.dto.BillTurnoverDto;
import com.example.bills.dto.KreditDebitDto;
import com.example.bills.dto.PeriodQueryDto;
import com.example.bills.dto.SumTransferDto;
import com.example.bills.services.BillService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы со счетами.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bills")
public class BillsController {

    /**
     * Сервис для работы со счетами.
     */
    private final BillService billsService;

    /**
     * Начислить на счет.
     *
     * @param dto Данные для начисления.
     */
    @PostMapping("/kredit")
    public void kredit(@RequestBody KreditDebitDto dto) {
        billsService.kredit(dto);
    }

    /**
     * Списать со счета.
     *
     * @param dto Данные для списания.
     */
    @PostMapping("/debit")
    public void debit(@RequestBody KreditDebitDto dto) {
        billsService.debit(dto);
    }

    /**
     * Перевести на другой счет.
     *
     * @param dto Данные для перевода.
     */
    @PostMapping("/transfer")
    public void transfer(@RequestBody SumTransferDto dto) {
        billsService.transfer(dto);
    }

    /**
     * Получить оборот по счету.
     *
     * @param dto Данные для получения оборота по счету.
     * @return Оборот (дебет, кредит)
     */
    @PostMapping("/turnover")
    public BillTurnoverDto turnover(@RequestBody PeriodQueryDto dto) {
        return billsService.turnover(dto);
    }

    /**
     * Получить постранично историю операций со счетами.
     *
     * @param dto      Данные для получения истории.
     * @param pageable Запрос страницы.
     * @return Страница истории операций.
     */
    @PostMapping("/history")
    public Page<BillHistoryDto> history(@RequestBody PeriodQueryDto dto, Pageable pageable) {
        return billsService.history(dto, pageable);
    }
}
