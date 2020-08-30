package com.example.bills.services;

import com.example.bills.dto.BillHistoryDto;
import com.example.bills.dto.BillTurnoverDto;
import com.example.bills.dto.KreditDebitDto;
import com.example.bills.dto.PeriodQueryDto;
import com.example.bills.dto.SumTransferDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис для работы со счетами.
 */
public interface BillService {
    /**
     * Начислить на счет.
     *
     * @param dto Данные для начисления.
     */
    void kredit(KreditDebitDto dto);

    /**
     * Списать со счета.
     *
     * @param dto Данные для списания.
     */
    void debit(KreditDebitDto dto);

    /**
     * Перевести на другой счет.
     *
     * @param dto Данные для перевода.
     */
    void transfer(SumTransferDto dto);

    /**
     * Получить оборот по счету.
     *
     * @param dto Данные для получения оборота по счету.
     * @return Оборот (дебет, кредит)
     */
    BillTurnoverDto turnover(PeriodQueryDto dto);

    /**
     * Получить постранично историю операций со счетами.
     *
     * @param dto      Данные для получения истории.
     * @param pageable Запрос страницы.
     * @return Страница истории операций.
     */
    Page<BillHistoryDto> history(PeriodQueryDto dto, Pageable pageable);
}
