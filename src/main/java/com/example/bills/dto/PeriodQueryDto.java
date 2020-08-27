package com.example.bills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PeriodQueryDto {
  private Long billId;
  private Long driverId;
  private Date startDate;
  private Date endDate;
}
