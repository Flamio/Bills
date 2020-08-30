package com.example.bills.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

/**
 * Счет.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Bill {

    /**
     * Идентификатор.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Текущая сумма.
     */
    @Column(nullable = false)
    private BigDecimal currentSum;

    /**
     * Название счета.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Водитель, которому принадлежит.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    /**
     * Операции по счету.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bill")
    private List<BillOperation> operations;
}
