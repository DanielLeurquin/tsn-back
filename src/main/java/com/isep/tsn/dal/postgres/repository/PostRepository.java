package com.isep.tsn.dal.postgres.repository;

import com.isep.tsn.dal.model.postgres.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
}
