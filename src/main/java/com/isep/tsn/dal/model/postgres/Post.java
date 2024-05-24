package com.isep.tsn.dal.model.postgres;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String postText;

    String userId;

    @CreationTimestamp
    LocalDateTime createdAt;

}
