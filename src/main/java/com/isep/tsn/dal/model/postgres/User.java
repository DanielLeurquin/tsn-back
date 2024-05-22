package com.isep.tsn.dal.model.postgres;

import com.isep.tsn.dal.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user", schema = "public")
public class User {

    @Id
    String id;

    String password;

    String email;

    @Enumerated(EnumType.STRING)
    Role role;
}
