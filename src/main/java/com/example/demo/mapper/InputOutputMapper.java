package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InputOutputMapper {

    OutputDto mapToOutput(InputDto inputDto);
}
