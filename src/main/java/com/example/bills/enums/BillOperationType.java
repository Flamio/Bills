package com.example.bills.enums;

/**
 * Тип операции.
 */
public enum BillOperationType {
    /**
     * Списание
     */
    DEBIT("Списание"),

    /**
     * Начилсение.
     */
    CREDIT("Начисление");

    BillOperationType(final String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
