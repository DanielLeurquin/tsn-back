package com.isep.tsn.controller;

import com.isep.tsn.dal.model.dto.UserAssignSubjectDto;
import com.isep.tsn.dal.model.dto.UserDto;
import com.isep.tsn.dal.model.dto.UserFriendDto;
import com.isep.tsn.mapper.UserMapper;
import com.isep.tsn.service.SecurityService;
import com.isep.tsn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUser(@RequestParam String id) {
        return ResponseEntity.ok(userService.searchUser(id));
    }

    @PostMapping("/addFriend/{friendId}")
    public ResponseEntity<UserDto> addFriend(@PathVariable String friendId) {
        return ResponseEntity.ok(userService.addFriend(friendId));
    }

    @GetMapping("/foaf")
    public ResponseEntity<List<UserFriendDto>> getFoafOfUser(@RequestParam int depth) {
        var user = userService.getUser(securityService.getLoggedId());
        return ResponseEntity.ok(userService.getFoafOfUser(user, depth)
                .stream()
                .map(UserMapper.instance()::convertToFriendDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("/assignSubject")
    public ResponseEntity<UserDto> assignSubject(@RequestBody UserAssignSubjectDto userAssignSubjectDto) {
        return ResponseEntity.ok(userService.userAssignSubject(userAssignSubjectDto));
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
