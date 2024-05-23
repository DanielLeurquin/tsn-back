package com.isep.tsn.dal.model.dto;

import com.isep.tsn.dal.model.postgres.User;
import lombok.Data;

@Data
public class PostDto {

    Long id;
    String postText;

    User user;

}
