package com.isep.tsn.service;

import com.isep.tsn.dal.model.dto.SubjectDto;
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

    public List<SubjectDto> findAll() {
        return subjectRepository.findAll()
                .stream()
                .map(SubjectMapper.instance()::convertToDto)
                .toList();
    }

}
