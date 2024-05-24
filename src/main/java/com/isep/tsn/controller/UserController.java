package com.isep.tsn.controller;

import com.isep.tsn.dal.model.dto.UserAssignSubjectDto;
import com.isep.tsn.dal.model.dto.UserDto;
import com.isep.tsn.dal.model.dto.UserFriendDto;
import com.isep.tsn.mapper.UserMapper;
import com.isep.tsn.service.SecurityService;
import com.isep.tsn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/search")
    public List<UserDto> searchUser(@RequestParam String id) {
        return userService.searchUser(id);
    }

    @PostMapping("/addFriend/{friendId}")
    public UserDto addFriend(@PathVariable String friendId) {
        return userService.addFriend(friendId);
    }

    @GetMapping("/foaf")
    public List<UserFriendDto> getFoafOfUser(@RequestParam int depth) {
        var user = userService.getUser(securityService.getLoggedId());
        return userService.getFoafOfUser(user, depth)
                .stream()
                .map(UserMapper.instance()::convertToFriendDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/assignSubject")
    public UserDto assignSubject(@RequestBody UserAssignSubjectDto userAssignSubjectDto) {
        return userService.userAssignSubject(userAssignSubjectDto);
    }


    @GetMapping("/friendRecommendation")
    public List<UserFriendDto> currentUserFriendRecommendation(@RequestParam Integer depth,
                                                                @RequestParam Double commonFriendsWeight,
                                                                @RequestParam Double commonSubjectsWeight) {
        return userService.currentUserFriendRecommendation(depth,
                commonFriendsWeight,
                commonSubjectsWeight);
    }

}
