package com.isep.tsn.controller;

import com.isep.tsn.dal.model.dto.CreatePostDto;
import com.isep.tsn.dal.model.dto.PostDto;
import com.isep.tsn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public PostDto addPost(@RequestBody CreatePostDto dto) {
        return postService.addPost(dto);
    }

    @GetMapping("/feed")
    public List<PostDto> getCurrentUserFeed(@RequestParam int depth,
                                            @RequestParam int recentWeight,
                                            @RequestParam int commonFriendsWeight,
                                            @RequestParam int commonSubjectsWeight) {
        return postService.getCurrentUserFeed(depth, recentWeight, commonFriendsWeight, commonSubjectsWeight);
    }


    @GetMapping("/test")
    public void test() {
        postService.recursiveQuickSortTest();
    }

}
