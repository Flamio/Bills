package com.example.bills.domain;

import com.example.bills.enums.BillOperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Операция по счету.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BillOperation {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип операции.
     */
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BillOperationType type;

    /**
     * Сумма.
     */
    @Column(nullable = false)
    private BigDecimal sum;

    /**
     * Дата.
     */
    @Column(nullable = false)
    private Date date;

    /**
     * Счет, с которым производилась операция.
     */
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
