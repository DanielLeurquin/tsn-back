package com.isep.tsn.service;

import com.isep.tsn.dal.model.dto.UserDto;
import com.isep.tsn.dal.model.postgres.User;
import com.isep.tsn.dal.postgres.repository.UserRepository;
import com.isep.tsn.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.instance()::convertToDto)
                .collect(Collectors.toList());
    }
}
