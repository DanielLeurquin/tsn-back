package com.isep.tsn.service;

import com.isep.tsn.dal.model.dto.SubjectDto;
import com.isep.tsn.dal.model.postgres.Subject;
import com.isep.tsn.dal.model.postgres.User;
import com.isep.tsn.dal.postgres.repository.SubjectRepository;
import com.isep.tsn.mapper.SubjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    UserService userService;

    public List<SubjectDto> findAll() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectMapper.instance()::convertToDto)
                .toList();
    }

    public SubjectDto addSubject(SubjectDto subjectDto) {
        var sub = new Subject();
        sub.setSubjectName(subjectDto.getSubjectName());

        return SubjectMapper.instance()
                .convertToDto(subjectRepository.save(sub));
                        
    }

    public List<SubjectDto> usersCommonSubjects(User user1, User user2){
        return user1.getSubjects()
                .stream()
                .filter(user2.getSubjects()::contains)
                .map(SubjectMapper.instance()::convertToDto)
                .toList();

    }

    public List<SubjectDto> getUserSubjects(User user){
        return user.getSubjects()
                .stream()
                .map(SubjectMapper.instance()::convertToDto)
                .toList();
    }

    public List<SubjectDto> getUserSubjects(String userId){
        var user = userService.getUser(userId);
        return user.getSubjects()
                .stream()
                .map(SubjectMapper.instance()::convertToDto)
                .toList();
    }

}
