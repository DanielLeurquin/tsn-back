package com.isep.tsn.service;

import com.isep.tsn.dal.model.dto.PostDto;
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

    public List<PostDto> findAll() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper.instance()::convertToDto)
                .toList();
    }

}
