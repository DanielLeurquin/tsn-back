package com.isep.tsn.dal.postgres.repository;

import com.isep.tsn.dal.model.dto.PostDto;
import com.isep.tsn.dal.model.postgres.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findByUserId(String userId);

}
