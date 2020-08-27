package com.example.bills.enums;

public enum BillOperationType {
  DEBIT("Списание"),
  CREDIT("Начисление"),
  TRANSFER("Перевод");

  BillOperationType(final String value) {
    this.value = value;
  }

  private String value;

  public String getValue() {
    return value;
  }
}
