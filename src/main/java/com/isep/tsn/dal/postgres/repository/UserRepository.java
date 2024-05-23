package com.isep.tsn.dal.postgres.repository;

import com.isep.tsn.dal.model.postgres.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findById(String id);

    List<User> findByIdContaining(String id);
}
