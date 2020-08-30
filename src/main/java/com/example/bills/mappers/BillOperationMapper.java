package com.example.bills.mappers;

import com.example.bills.domain.BillOperation;
import com.example.bills.dto.BillHistoryDto;
import com.example.bills.enums.BillOperationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Маппер операции по счету из сущности БД в dto.
 */
@Mapper(componentModel = "spring")
public interface BillOperationMapper {
  @Mapping(source = "type", target = "type", qualifiedByName = "typeTransformation")
  BillHistoryDto toBillHistoryDto(BillOperation billOperation);

  @Named("typeTransformation")
  default String getTypeValue(BillOperationType type) {
    return type.getValue();
  }
}
