package com.example.bills.services.impl;

import com.example.bills.domain.Bill;
import com.example.bills.domain.BillOperation;
import com.example.bills.dto.BillHistoryDto;
import com.example.bills.dto.BillTurnoverDto;
import com.example.bills.dto.KreditDebitDto;
import com.example.bills.dto.PeriodQueryDto;
import com.example.bills.dto.SumTransferDto;
import com.example.bills.enums.BillOperationType;
import com.example.bills.exceptions.BadRequestException;
import com.example.bills.exceptions.BillNotFoundException;
import com.example.bills.mappers.BillOperationMapper;
import com.example.bills.repositories.BillOperationRepository;
import com.example.bills.repositories.BillRepository;
import com.example.bills.services.BillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultBillService implements BillService {

    private final BillRepository billRepository;
    private final BillOperationRepository billOperationRepository;
    private final BillOperationMapper billOperationMapper;

    @Override
    @Transactional
    public void kredit(KreditDebitDto dto) {
        checkDriverBill(dto.getBillId(), dto.getDriverId());

        final Bill bill = billRepository.findById(dto.getBillId()).get();
        bill.setCurrentSum(bill.getCurrentSum().add(dto.getSum()));

        final BillOperation billOperation =
                BillOperation.builder()
                        .bill(bill)
                        .date(new Date())
                        .sum(dto.getSum())
                        .type(BillOperationType.CREDIT)
                        .build();

        bill.getOperations().add(billOperation);

        billRepository.save(bill);
        log.info("kredit bill: {}. Sum: {}", bill.getId(), dto.getSum());
    }

    @Override
    @Transactional
    public void debit(KreditDebitDto dto) {
        checkDriverBill(dto.getBillId(), dto.getDriverId());

        final Bill bill = billRepository.findById(dto.getBillId()).get();

        if (bill.getCurrentSum().subtract(dto.getSum()).compareTo(BigDecimal.ZERO) < 0) {
            log.error("bill {} has no enought money to debit {}", bill.getId(), dto.getSum());
            throw new BadRequestException("not enought money to debit");
        }

        bill.setCurrentSum(bill.getCurrentSum().subtract(dto.getSum()));

        final BillOperation billOperation =
                BillOperation.builder()
                        .bill(bill)
                        .date(new Date())
                        .sum(dto.getSum())
                        .type(BillOperationType.DEBIT)
                        .build();

        bill.getOperations().add(billOperation);

        billRepository.save(bill);
        log.info(" debit bill: {}. Sum: {}", bill.getId(), dto.getSum());
    }

    @Override
    @Transactional
    public void transfer(final SumTransferDto dto) {
        checkDriverBill(dto.getBillId(), dto.getDriverId());
        checkDriverBill(dto.getDestinationBillId(), dto.getDriverId());

        final Bill bill = billRepository.findById(dto.getBillId()).get();

        if (bill.getCurrentSum().subtract(dto.getSum()).compareTo(BigDecimal.ZERO) < 0) {
            log.error("bill {} has no enough money to transfer {}", bill.getId(), dto.getSum());
            throw new BadRequestException("not enough money to transfer");
        }

        bill.setCurrentSum(bill.getCurrentSum().subtract(dto.getSum()));
        final BillOperation billOperation =
                BillOperation.builder()
                        .bill(bill)
                        .date(new Date())
                        .sum(dto.getSum())
                        .type(BillOperationType.DEBIT)
                        .build();
        bill.getOperations().add(billOperation);

        billRepository.save(bill);

        final Bill destinationBill = billRepository.findById(dto.getDestinationBillId()).get();
        destinationBill.setCurrentSum(destinationBill.getCurrentSum().add(dto.getSum()));
        final BillOperation destinationBillOperation =
                BillOperation.builder()
                        .bill(destinationBill)
                        .date(new Date())
                        .sum(dto.getSum())
                        .type(BillOperationType.CREDIT)
                        .build();
        bill.getOperations().add(destinationBillOperation);
        billRepository.save(destinationBill);

        log.info("transfer from bill {} to bill {} with sum: {}", bill.getId(), destinationBill.getId(), dto.getSum());
    }

    @Override
    public BillTurnoverDto turnover(final PeriodQueryDto dto) {
        checkDriverBill(dto.getBillId(), dto.getDriverId());

        if (!billRepository.existsById(dto.getBillId()))
            throw new BillNotFoundException("bill not found");

        final List<BillOperation> creditOperations =
                billOperationRepository.findAllByBillIdAndTypeAndDateBetween(
                        dto.getBillId(), BillOperationType.CREDIT, dto.getStartDate(), dto.getEndDate());

        final List<BillOperation> debitOperations =
                billOperationRepository.findAllByBillIdAndTypeAndDateBetween(
                        dto.getBillId(), BillOperationType.DEBIT, dto.getStartDate(), dto.getEndDate());

        final BigDecimal debit =
                debitOperations.stream()
                        .map(BillOperation::getSum)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal credit =
                creditOperations.stream()
                        .map(BillOperation::getSum)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return BillTurnoverDto.builder().debit(debit).kredit(credit).build();
    }

    @Override
    public Page<BillHistoryDto> history(final PeriodQueryDto dto, Pageable pageable) {
        final Page<BillOperation> operations =
                billOperationRepository.findAllByDateBetween(
                        dto.getStartDate(), dto.getEndDate(), pageable);

        return operations.map(billOperation -> {
            final BillHistoryDto billHistoryDto = billOperationMapper.toBillHistoryDto(billOperation);

            billHistoryDto.setDetails(String.format("Водитель: %s, счет: %s", billOperation.getBill().getDriver().getFio(), billOperation.getBill().getName()));
            return billHistoryDto;
        });
    }

    private void checkDriverBill(Long billId, Long driverId) {
        if (billRepository.existsByIdAndAndDriverId(billId, driverId))
            return;

        final String message = String.format("no bill (%s) for driver (%s)", billId, driverId);
        log.error(message);
        throw new BadRequestException(message);
    }
}
