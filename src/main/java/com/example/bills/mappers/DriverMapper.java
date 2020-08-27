package com.example.bills.mappers;

import com.example.bills.domain.Driver;
import com.example.bills.dto.DriverDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DriverMapper {
  DriverDto toDriverDto(Driver driver);
}
