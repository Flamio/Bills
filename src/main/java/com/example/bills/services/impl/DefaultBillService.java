package com.example.bills.services.impl;

import com.example.bills.domain.Bill;
import com.example.bills.domain.BillOperation;
import com.example.bills.dto.KreditDebitDto;
import com.example.bills.dto.SumTransferDto;
import com.example.bills.enums.BillOperationType;
import com.example.bills.exceptions.BadRequestException;
import com.example.bills.repositories.BillRepository;
import com.example.bills.services.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class DefaultBillService implements BillService {

  private final BillRepository billRepository;

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
  }

  @Override
  @Transactional
  public void debit(KreditDebitDto dto) {
    checkDriverBill(dto.getBillId(), dto.getDriverId());

    final Bill bill = billRepository.findById(dto.getBillId()).get();
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
  }

  @Override
  @Transactional
  public void transfer(final SumTransferDto dto) {
    checkDriverBill(dto.getBillId(), dto.getDriverId());
    checkDriverBill(dto.getDestinationBillId(), dto.getDriverId());

    final Bill bill = billRepository.findById(dto.getBillId()).get();
    bill.setCurrentSum(bill.getCurrentSum().subtract(dto.getSum()));
    final BillOperation billOperation =
        BillOperation.builder()
            .bill(bill)
            .date(new Date())
            .sum(dto.getSum())
            .type(BillOperationType.TRANSFER)
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
            .type(BillOperationType.TRANSFER)
            .build();
    bill.getOperations().add(destinationBillOperation);
    billRepository.save(destinationBill);
  }

  private void checkDriverBill(Long billId, Long driverId) {
    if (!billRepository.existsByIdAndAndDriverId(billId, driverId))
      throw new BadRequestException(
          String.format("no bill (%s) for driver (%s)", billId, driverId));
  }
}
