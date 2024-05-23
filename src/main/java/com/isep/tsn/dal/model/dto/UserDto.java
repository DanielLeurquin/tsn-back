package com.isep.tsn.dal.model.dto;

import com.isep.tsn.dal.model.enums.Role;
import com.isep.tsn.dal.model.postgres.User;
import lombok.Data;

import java.util.List;


@Data
public class UserDto {

    String id;

    String email;

    Role role;

    List<PostDto> posts;

    List<UserFriendDto> friends;

}
