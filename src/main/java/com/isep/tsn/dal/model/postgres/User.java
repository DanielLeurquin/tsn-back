package com.isep.tsn.dal.model.postgres;

import com.isep.tsn.dal.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @OneToMany(mappedBy = "userId")
    List<Post> posts;

    @ManyToMany
    @JoinTable(name = "user_subject", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_name"))
    List<Subject> subjects;

    @ManyToMany
    @JoinTable(name = "user_friend", joinColumns = @JoinColumn(name = "user1"),
            inverseJoinColumns = @JoinColumn(name = "user2"))
    List<UserFriend> friends;
}
