package com.isep.tsn.mapper;

import com.isep.tsn.dal.model.dto.UserDto;
import com.isep.tsn.dal.model.dto.UserFriendDto;
import com.isep.tsn.dal.model.dto.UserRegisterDto;
import com.isep.tsn.dal.model.postgres.User;
import com.isep.tsn.dal.model.postgres.UserFriend;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {PostMapper.class, SubjectMapper.class})
public interface UserMapper {

    static UserMapper instance() {
        return Mappers.getMapper(UserMapper.class);
    }

    UserDto convertToDto(User user);

    UserFriend convertToUserFriend(User user);

    UserFriendDto convertToFriendDto(User user);
    User convertToEntity(UserRegisterDto userRegisterDto);

}
