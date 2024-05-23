package com.isep.tsn.controller;

import com.isep.tsn.dal.model.dto.PostDto;
import com.isep.tsn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public List<PostDto> getPosts() {
        return postService.findAll();
    }

}
