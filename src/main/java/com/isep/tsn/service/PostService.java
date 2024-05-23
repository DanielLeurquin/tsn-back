package com.isep.tsn.service;

import com.isep.tsn.dal.model.dto.CreatePostDto;
import com.isep.tsn.dal.model.dto.PostDto;
import com.isep.tsn.dal.model.postgres.Post;
import com.isep.tsn.dal.postgres.repository.PostRepository;
import com.isep.tsn.mapper.PostMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    public List<PostDto> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper.instance()::convertToDto)
                .toList();
    }

    public PostDto addPost(CreatePostDto dto) {
        System.out.println(dto);
        var post = new Post();
        post.setPostText(dto.getPostText());
        post.setUserId(dto.getUserId());

        return PostMapper.instance()
                .convertToDto(postRepository.save(post));

    }
}
