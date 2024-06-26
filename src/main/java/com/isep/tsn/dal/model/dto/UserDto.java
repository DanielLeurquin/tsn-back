package com.isep.tsn.dal.model.dto;

import com.isep.tsn.dal.model.enums.Role;
import com.isep.tsn.dal.model.postgres.Subject;
import com.isep.tsn.dal.model.postgres.User;
import lombok.Data;

import java.util.List;


@Data
public class UserDto {

    String id;

    String email;

    Role role;

    String name;

    String lastName;

    List<PostDto> posts;

    List<SubjectDto> subjects;

    List<UserFriendDto> friends;

}
