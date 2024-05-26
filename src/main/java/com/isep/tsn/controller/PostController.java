package com.isep.tsn.controller;

import com.isep.tsn.dal.model.dto.CreatePostDto;
import com.isep.tsn.dal.model.dto.PostDto;
import com.isep.tsn.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @PostMapping
    public ResponseEntity<PostDto> addPost(@RequestBody CreatePostDto dto) {

        return ResponseEntity.ok(postService.addPost(dto));
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getCurrentUserFeed(@RequestParam int depth,
                                                            @RequestParam int recentWeight,
                                                            @RequestParam int commonFriendsWeight,
                                                            @RequestParam int commonSubjectsWeight) {
        return ResponseEntity.ok(postService.getCurrentUserFeed(depth,
                recentWeight, commonFriendsWeight, commonSubjectsWeight));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<PostDto>> byUserId(@PathVariable String userId) {
        return ResponseEntity.ok(postService.getPostFromUserId(userId));
    }

}
