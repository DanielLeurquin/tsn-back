package com.isep.tsn.service;

import com.isep.tsn.dal.model.dto.UserAssignSubjectDto;
import com.isep.tsn.dal.model.dto.UserDto;
import com.isep.tsn.dal.model.dto.UserFriendDto;
import com.isep.tsn.dal.model.postgres.User;
import com.isep.tsn.dal.model.postgres.UserFriend;
import com.isep.tsn.dal.postgres.repository.SubjectRepository;
import com.isep.tsn.dal.postgres.repository.UserRepository;
import com.isep.tsn.exceptions.BusinessException;
import com.isep.tsn.helper.Helper;
import com.isep.tsn.helper.QuickSortList;
import com.isep.tsn.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SubjectService subjectService;

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
        var user2 = userRepository.findById(friendId)
                .orElseThrow();

        user1.getFriends()
                .add(UserToUserFriend(user2));
        user2.getFriends()
                .add(UserToUserFriend(user1));

        userRepository.save(user2);
        return UserMapper.instance()
                .convertToDto(userRepository.save(user1));

    }

    public UserFriend UserToUserFriend(User user) {
        var userFriend = new UserFriend();
        userFriend.setId(user.getId());
        userFriend.setRole(user.getRole());
        userFriend.setEmail(user.getEmail());
        userFriend.setPassword(user.getPassword());
        return userFriend;
    }

    public List<User> userListFromUserFriendList(List<UserFriend> userFriendList) {
        List<User> userList = new ArrayList<>();
        for (UserFriend userFriend : userFriendList) {
            userList.add(getUser(userFriend.getId()));
        }
        return userList;
    }


    //todo change this to exclude user already in friend list
    public List<User> getFoafOfUser(User user, int depth) {
        if (depth == 0) {
            return List.of();
        }
        List<User> finalList = new ArrayList<>(userListFromUserFriendList(user.getFriends()));
        var friendList = userListFromUserFriendList(user.getFriends());
        for (User u : friendList) {
            finalList.addAll(getFoafOfUser(u, depth - 1));
        }
        var unique = finalList.stream()
                .distinct()
                .collect(Collectors.toList());

        if (unique.contains(user)) {
            unique.remove(user);
        }

        return unique;

    }

    public UserDto userAssignSubject(UserAssignSubjectDto dto) {
        var user = getUser(dto.getUserId());
        var subjects = user.getSubjects();

        var subjectOpt = subjectRepository.findById(dto.getSubjectName());

        if (subjectOpt.isEmpty()) {
            throw new BusinessException("Subject not found");
        }

        if (subjects.contains(subjectOpt.get())) {
            throw new BusinessException("User already has this subject");
        }

        subjects.add(subjectOpt.get());

        return UserMapper.instance()
                .convertToDto(userRepository.save(user));
    }

    public Integer usersCommonFriendsNumber(User user1, User user2) {
        return user1.getFriends()
                .stream()
                .filter(user2.getFriends()::contains)
                .collect(Collectors.toList())
                .size();

    }

    public List<UserFriendDto> currentUserFriendRecommendation(int depth,
                                                               Double commonFriendsWeight,
                                                               Double subjectWeight) {

        var currentUser = getUser(securityService.getLoggedId());
        var foaf = getFoafOfUser(currentUser, depth);
        foaf.removeAll(userListFromUserFriendList(currentUser.getFriends()));

        var friendWeights = new ArrayList<Integer>();
        var subjectWeights = new ArrayList<Integer>();

        for (User u : foaf) {
            var commonFriends = usersCommonFriendsNumber(currentUser, u);
            var commonSubjects = subjectService.usersCommonSubjects(currentUser, u)
                    .size();
            friendWeights.add(commonFriends);
            subjectWeights.add(commonSubjects);
        }
        var linearFriendWeights = Helper.lineariseWeights(friendWeights, 1, 10);
        var linearSubjectWeights = Helper.lineariseWeights(subjectWeights, 1, 10);

        var weights = new ArrayList<Double>();

        for (int i = 0; i < foaf.size(); i++) {
            weights.add(commonFriendsWeight * linearFriendWeights.get(i) +
                    subjectWeight * linearSubjectWeights.get(i));
        }
        System.out.println(foaf.stream().map(u -> u.getId()).collect(Collectors.toList()));
        System.out.println(weights);
        var userRecommendation =
                (List<User>) Helper.recursiveQuickSort(new QuickSortList<User>(foaf, weights))
                        .getObjects();
        Collections.reverse(userRecommendation);
        System.out.print("Output");
        System.out.println(userRecommendation.stream().map(u -> u.getId()).collect(Collectors.toList()));

        return userRecommendation.stream()
                .map(UserMapper.instance()::convertToFriendDto)
                .collect(Collectors.toList());
    }


}
