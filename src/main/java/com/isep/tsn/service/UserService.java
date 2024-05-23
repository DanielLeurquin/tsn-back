package com.isep.tsn.service;

import com.isep.tsn.dal.model.dto.UserDto;
import com.isep.tsn.dal.model.postgres.User;
import com.isep.tsn.dal.model.postgres.UserFriend;
import com.isep.tsn.dal.postgres.repository.UserRepository;
import com.isep.tsn.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityService securityService;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.instance()::convertToDto)
                .collect(Collectors.toList());
    }


    public List<UserDto> searchUser(String id) {
        return userRepository.findByIdContaining(id)
                .stream()
                .map(UserMapper.instance()::convertToDto)
                .collect(Collectors.toList());
    }

    public User getUser(String id) {
        return userRepository.findById(id)
                .orElseThrow();
    }

    //todo make the same thing with algorithm implementation
    public UserDto addFriend(String friendId) {
        var user1 = getUser(securityService.getLoggedId());
        var user2 = userRepository.findById(friendId).orElseThrow();

        user1.getFriends()
                .add(UserToUserFriend(user2));
        user2.getFriends()
                .add(UserToUserFriend(user1));

        userRepository.save(user2);
        return UserMapper.instance()
                .convertToDto(userRepository.save(user1));

    }
     public UserFriend UserToUserFriend(User user){
        var userFriend = new UserFriend();
        userFriend.setId(user.getId());
        userFriend.setRole(user.getRole());
        userFriend.setEmail(user.getEmail());
        userFriend.setPassword(user.getPassword());
        return userFriend;
     }

    public List<User> userListFromUserFriendList(List<UserFriend> userFriendList){
        List<User> userList = new ArrayList<>();
        for(UserFriend userFriend : userFriendList){
            userList.add(getUser(userFriend.getId()));
        }
        return userList;
    }

    public List<User> getFoafOfUser(User user, int depth){
        if(depth == 0){
            return List.of();
        }
        List<User> finalList = new ArrayList<>(userListFromUserFriendList(user.getFriends()));
        var friendList = userListFromUserFriendList(user.getFriends());
        for(User u : friendList){
            finalList.addAll(getFoafOfUser(u, depth-1));
        }
        var unique = finalList.stream().distinct().collect(Collectors.toList());
        if(unique.contains(user)){
            unique.remove(user);
        }

        return unique;

    }


}
