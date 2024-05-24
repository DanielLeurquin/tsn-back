package com.isep.tsn.dal.model.dto;

import com.isep.tsn.dal.model.postgres.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {

    Long id;
    String postText;
    String userId;

    LocalDateTime createdAt;

}
