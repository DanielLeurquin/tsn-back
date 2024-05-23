package com.isep.tsn.mapper;

import com.isep.tsn.dal.model.dto.SubjectDto;
import com.isep.tsn.dal.model.postgres.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectMapper {
    static SubjectMapper instance() {
        return Mappers.getMapper(SubjectMapper.class);
    }

    SubjectDto convertToDto(Subject subject);
}
