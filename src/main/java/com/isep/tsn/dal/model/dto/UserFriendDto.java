package com.isep.tsn.dal.model.dto;

import com.isep.tsn.dal.model.enums.Role;

import lombok.Data;

import java.util.List;

@Data
public class UserFriendDto {

    String id;

    String email;

    String name;

    String lastName;

    Role role;

}
