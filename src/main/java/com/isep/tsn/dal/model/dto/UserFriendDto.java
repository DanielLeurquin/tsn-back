package com.isep.tsn.dal.model.dto;

import com.isep.tsn.dal.model.enums.Role;

import lombok.Data;

import java.util.List;

@Data
public class UserFriendDto {

    String id;

    String email;

    Role role;

}
