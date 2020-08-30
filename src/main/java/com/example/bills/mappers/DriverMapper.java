package com.example.bills.mappers;

import com.example.bills.domain.Driver;
import com.example.bills.dto.DriverDto;
import org.mapstruct.Mapper;

/**
 * Маппер водителя из сущности БД в dto.
 */
@Mapper(componentModel = "spring")
public interface DriverMapper {
  DriverDto toDriverDto(Driver driver);
}
