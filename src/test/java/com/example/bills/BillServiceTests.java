package com.example.bills;

import com.example.bills.domain.Bill;
import com.example.bills.domain.BillOperation;
import com.example.bills.dto.BillTurnoverDto;
import com.example.bills.dto.KreditDebitDto;
import com.example.bills.dto.PeriodQueryDto;
import com.example.bills.dto.SumTransferDto;
import com.example.bills.enums.BillOperationType;
import com.example.bills.exceptions.BadRequestException;
import com.example.bills.exceptions.BillNotFoundException;
import com.example.bills.mappers.BillOperationMapper;
import com.example.bills.mappers.BillOperationMapperImpl;
import com.example.bills.repositories.BillOperationRepository;
import com.example.bills.repositories.BillRepository;
import com.example.bills.services.BillService;
import com.example.bills.services.impl.DefaultBillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class BillServiceTests {

  private BillRepository billRepository;
  private BillOperationRepository billOperationRepository;
  private BillOperationMapper billOperationMapper = new BillOperationMapperImpl();
  private BillService billService;
  private ArgumentCaptor<Bill> billArgumentCaptor = ArgumentCaptor.forClass(Bill.class);

  @BeforeEach
  public void setUp() {
    billRepository = Mockito.mock(BillRepository.class);
    billOperationRepository = Mockito.mock(BillOperationRepository.class);
    billService =
        new DefaultBillService(billRepository, billOperationRepository, billOperationMapper);
  }

  @Test
  public void kreditShouldIncreaseCurrentSumAndAddRightOperation() {
    // arrange
    final BigDecimal currentSum = BigDecimal.valueOf(5000);
    final BigDecimal increasingSum = BigDecimal.valueOf(55);

    when(billRepository.existsByIdAndAndDriverId(Mockito.any(), Mockito.any())).thenReturn(true);

    when(billRepository.findById(Mockito.any()))
        .thenReturn(
            Optional.of(
                Bill.builder().currentSum(currentSum).operations(new ArrayList<>()).build()));

    // act

    billService.kredit(KreditDebitDto.builder().sum(increasingSum).driverId(1L).billId(1L).build());

    // assert
    verify(billRepository, times(1)).save(billArgumentCaptor.capture());

    assertThat(billArgumentCaptor.getValue().getCurrentSum())
        .isEqualTo(currentSum.add(increasingSum));
    assertThat(billArgumentCaptor.getValue().getOperations().get(0).getSum())
        .isEqualTo(increasingSum);
    assertThat(billArgumentCaptor.getValue().getOperations().get(0).getType())
        .isEqualTo(BillOperationType.KREDIT);
  }

  @Test
  public void debitShouldDecreaseCurrentSumAndAddRightOperation() {
    // arrange
    final BigDecimal currentSum = BigDecimal.valueOf(5000);
    final BigDecimal decreasingSum = BigDecimal.valueOf(55);

    when(billRepository.existsByIdAndAndDriverId(Mockito.any(), Mockito.any())).thenReturn(true);

    when(billRepository.findById(Mockito.any()))
        .thenReturn(
            Optional.of(
                Bill.builder().currentSum(currentSum).operations(new ArrayList<>()).build()));

    // act

    billService.debit(KreditDebitDto.builder().sum(decreasingSum).driverId(1L).billId(1L).build());

    // assert
    verify(billRepository, times(1)).save(billArgumentCaptor.capture());

    assertThat(billArgumentCaptor.getValue().getCurrentSum())
        .isEqualTo(currentSum.subtract(decreasingSum));
    assertThat(billArgumentCaptor.getValue().getOperations().get(0).getSum())
        .isEqualTo(decreasingSum);
    assertThat(billArgumentCaptor.getValue().getOperations().get(0).getType())
        .isEqualTo(BillOperationType.DEBIT);
  }

  @Test
  public void debitMoreThanCurrentSumShouldThroughBadRequestException() {
    // arrange
    final BigDecimal currentSum = BigDecimal.valueOf(5);
    final BigDecimal decreasingSum = BigDecimal.valueOf(55);

    when(billRepository.existsByIdAndAndDriverId(Mockito.any(), Mockito.any())).thenReturn(true);

    when(billRepository.findById(Mockito.any()))
        .thenReturn(
            Optional.of(
                Bill.builder().currentSum(currentSum).operations(new ArrayList<>()).build()));

    // act

    final Throwable throwable =
        catchThrowable(
            () -> {
              billService.debit(
                  KreditDebitDto.builder().sum(decreasingSum).driverId(1L).billId(1L).build());
            });

    // assert

    verify(billRepository, times(0)).save(billArgumentCaptor.capture());
    assertThat(throwable).isInstanceOf(BadRequestException.class);
  }

  @Test
  public void transferShouldDecreaseCurrentSumAndIncreaseCurrentSumInDestinationBill() {
    // arrange
    final BigDecimal currentSumBill1 = BigDecimal.valueOf(500);
    final BigDecimal currentSumBill2 = BigDecimal.valueOf(700);
    final BigDecimal transferSum = BigDecimal.valueOf(77);

    final Long billId1 = 1L;
    final Long billId2 = 2L;

    when(billRepository.existsByIdAndAndDriverId(Mockito.any(), Mockito.any())).thenReturn(true);

    when(billRepository.findById(billId1))
        .thenReturn(
            Optional.of(
                Bill.builder().currentSum(currentSumBill1).operations(new ArrayList<>()).build()));

    when(billRepository.findById(billId2))
        .thenReturn(
            Optional.of(
                Bill.builder().currentSum(currentSumBill2).operations(new ArrayList<>()).build()));

    final SumTransferDto sumTransferDto = new SumTransferDto();
    sumTransferDto.setDestinationBillId(billId2);
    sumTransferDto.setBillId(billId1);
    sumTransferDto.setDriverId(1L);
    sumTransferDto.setSum(transferSum);

    // act

    billService.transfer(sumTransferDto);

    // assert

    verify(billRepository, times(2)).save(billArgumentCaptor.capture());

    assertThat(billArgumentCaptor.getAllValues().get(0).getCurrentSum())
        .isEqualTo(currentSumBill1.subtract(transferSum));
    assertThat(billArgumentCaptor.getAllValues().get(0).getOperations().get(0).getType())
        .isEqualTo(BillOperationType.DEBIT);
    assertThat(billArgumentCaptor.getAllValues().get(0).getOperations().get(0).getSum())
        .isEqualTo(transferSum);

    assertThat(billArgumentCaptor.getAllValues().get(1).getCurrentSum())
        .isEqualTo(currentSumBill2.add(transferSum));
    assertThat(billArgumentCaptor.getAllValues().get(1).getOperations().get(0).getType())
        .isEqualTo(BillOperationType.KREDIT);
    assertThat(billArgumentCaptor.getAllValues().get(1).getOperations().get(0).getSum())
        .isEqualTo(transferSum);
  }

  @Test
  public void transferMoreThanCurrentSumShouldThrowBadRequestException() {
    // arrange
    final BigDecimal currentSumBill1 = BigDecimal.valueOf(500);
    final BigDecimal currentSumBill2 = BigDecimal.valueOf(700);
    final BigDecimal transferSum = BigDecimal.valueOf(800);

    final Long billId1 = 1L;
    final Long billId2 = 2L;

    when(billRepository.existsByIdAndAndDriverId(Mockito.any(), Mockito.any())).thenReturn(true);

    when(billRepository.findById(billId1))
        .thenReturn(
            Optional.of(
                Bill.builder().currentSum(currentSumBill1).operations(new ArrayList<>()).build()));

    when(billRepository.findById(billId2))
        .thenReturn(
            Optional.of(
                Bill.builder().currentSum(currentSumBill2).operations(new ArrayList<>()).build()));

    final SumTransferDto sumTransferDto = new SumTransferDto();
    sumTransferDto.setDestinationBillId(billId2);
    sumTransferDto.setBillId(billId1);
    sumTransferDto.setDriverId(1L);
    sumTransferDto.setSum(transferSum);

    // act

    final Throwable throwable =
        catchThrowable(
            () -> {
              billService.transfer(sumTransferDto);
            });

    // assert

    verify(billRepository, times(0)).save(billArgumentCaptor.capture());
    assertThat(throwable).isInstanceOf(BadRequestException.class);
  }

  @Test
  public void turnoverWhenBillNotExistsShouldThrowNotFoundException() {
    // arrange

    when(billRepository.existsById(any())).thenReturn(false);
    when(billRepository.existsByIdAndAndDriverId(Mockito.any(), Mockito.any())).thenReturn(true);

    // act

    final Throwable throwable =
        catchThrowable(
            () -> {
              billService.turnover(new PeriodQueryDto());
            });

    // assert

    assertThat(throwable).isInstanceOf(BillNotFoundException.class);
  }

  @Test
  public void turnoverShouldReturnRightDebitAndCredit() {

    // arrange

    when(billRepository.existsById(any())).thenReturn(true);
    when(billRepository.existsByIdAndAndDriverId(Mockito.any(), Mockito.any())).thenReturn(true);

    final Date start = new Date();
    start.setMonth(01);
    start.setYear(2020);

    final Date end = new Date();
    end.setMonth(02);
    end.setYear(2020);

    final PeriodQueryDto periodQueryDto =
        PeriodQueryDto.builder().driverId(1L).billId(1L).startDate(start).endDate(end).build();

    when(billOperationRepository.findAllByBillIdAndTypeAndDateBetween(
            periodQueryDto.getBillId(), BillOperationType.KREDIT, start, end))
        .thenReturn(
            List.of(
                BillOperation.builder().sum(BigDecimal.valueOf(455)).build(),
                BillOperation.builder().sum(BigDecimal.valueOf(399)).build(),
                BillOperation.builder().sum(BigDecimal.valueOf(32)).build()));

    when(billOperationRepository.findAllByBillIdAndTypeAndDateBetween(
            periodQueryDto.getBillId(), BillOperationType.DEBIT, start, end))
        .thenReturn(
            List.of(
                BillOperation.builder().sum(BigDecimal.valueOf(32)).build(),
                BillOperation.builder().sum(BigDecimal.valueOf(65)).build(),
                BillOperation.builder().sum(BigDecimal.valueOf(78)).build()));

    // act

    final BillTurnoverDto turnover = billService.turnover(periodQueryDto);

    // assert

    assertThat(turnover.getDebit()).isEqualTo(BigDecimal.valueOf(175));
    assertThat(turnover.getKredit()).isEqualTo(BigDecimal.valueOf(886));
  }

  @Test
  public void operationsWithBillWithWrongDriverShouldThroughBadRequestException() {

    // arrange

    Mockito.when(billRepository.existsByIdAndAndDriverId(Mockito.any(), Mockito.any()))
        .thenReturn(false);

    // act

    final Throwable transferThrowable =
        catchThrowable(
            () -> {
              billService.transfer(new SumTransferDto());
            });

    final Throwable debitThrowable =
        catchThrowable(
            () -> {
              billService.debit(new KreditDebitDto());
            });

    final Throwable kreditThrowable =
        catchThrowable(
            () -> {
              billService.kredit(new KreditDebitDto());
            });

    final Throwable turnoverThrowable =
        catchThrowable(
            () -> {
              billService.turnover(new PeriodQueryDto());
            });

    // assert

    assertThat(transferThrowable).isInstanceOf(BadRequestException.class);
    assertThat(debitThrowable).isInstanceOf(BadRequestException.class);
    assertThat(kreditThrowable).isInstanceOf(BadRequestException.class);
    assertThat(turnoverThrowable).isInstanceOf(BadRequestException.class);
  }
}
