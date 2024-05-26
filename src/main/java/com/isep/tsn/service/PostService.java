package com.isep.tsn.service;

import com.isep.tsn.dal.model.dto.CreatePostDto;
import com.isep.tsn.dal.model.dto.PostDto;
import com.isep.tsn.dal.model.postgres.Post;
import com.isep.tsn.dal.postgres.repository.PostRepository;
import com.isep.tsn.helper.Helper;
import com.isep.tsn.helper.QuickSortList;
import com.isep.tsn.mapper.PostMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @Autowired
    SubjectService subjectService;

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

    public List<PostDto> getPostFromUserId(String userId) {
        return postRepository.findByUserId(userId)
                .stream()
                .map(PostMapper.instance()::convertToDto)
                .toList();
    }


    public List<PostDto> getCurrentUserFeed(int depth,
                                            int recentWeight,
                                            int commonFriendsWeight,
                                            int commonSubjectsWeight) {

        var currentUser = this.userService.getUser(this.securityService.getLoggedId());

        List<Post> unsortedPostList = this.userService.getFoafOfUser(currentUser, depth)
                .stream()
                .flatMap(u -> u.getPosts()
                        .stream())
                .toList();

        var dateWeights = new ArrayList<Double>();
        var commonSubjects = new ArrayList<Integer>();
        var commonFriends = new ArrayList<Integer>();


        var now = new Date().getTime() / 1000;

        for (Post post : unsortedPostList) {
            var user = this.userService.getUser(post.getUserId());
            ZonedDateTime zdt = ZonedDateTime.of(post.getCreatedAt(),
                    ZoneId.systemDefault());
            long date = zdt.toInstant()
                    .toEpochMilli() / 1000;

            if (now - date == 0) {
                continue;
            }

            dateWeights.add((double) 1 / (now - date));
            commonSubjects.add(subjectService.usersCommonSubjects(currentUser, user)
                    .size());
            commonFriends.add(userService.usersCommonFriendsNumber(currentUser, user));
        }

        //adapt dateWeights to be between 1 and 10
        var linearDateWeights = Helper.lineariseWeights(dateWeights, 1, 10);
        var linearCommonSubjects = Helper.lineariseWeights(commonSubjects, 1, 10);
        var linearCommonFriends = Helper.lineariseWeights(commonFriends, 1, 10);


        var finalWeights = new ArrayList<Double>();

        for(int i = 0; i < unsortedPostList.size(); i++){
            finalWeights.add(
                    recentWeight * linearDateWeights.get(i) +
                            commonFriendsWeight * linearCommonFriends.get(i) +
                            commonSubjectsWeight * linearCommonSubjects.get(i)
            );
        }

        var postRecommendation = (List<Post>) Helper.recursiveQuickSort(
                new QuickSortList<Post>(unsortedPostList, finalWeights)
        ).getObjects();
        Collections.reverse(postRecommendation);
        return postRecommendation.stream().map(PostMapper.instance()::convertToDto)
                .toList();
    }

    public void recursiveQuickSortTest(){
        var list = new QuickSortList<String>(List.of("a","b","c","d","e"),
                List.of(1.0, 5.5, 10.0, 1.0, 1.0));
        System.out.println(Helper.recursiveQuickSort(list));
    }

}
