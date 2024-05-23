package com.isep.tsn.mapper;

import com.isep.tsn.dal.model.dto.PostDto;
import com.isep.tsn.dal.model.postgres.Post;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    static PostMapper instance() {
        return Mappers.getMapper(PostMapper.class);
    }

    PostDto convertToDto(Post post);

}
