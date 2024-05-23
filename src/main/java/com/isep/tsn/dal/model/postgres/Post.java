package com.isep.tsn.dal.model.postgres;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String postText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
