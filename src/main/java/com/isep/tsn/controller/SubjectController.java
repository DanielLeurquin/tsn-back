package com.isep.tsn.controller;

import com.isep.tsn.dal.model.dto.SubjectDto;
import com.isep.tsn.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping
    public List<SubjectDto> getSubjects() {
        return subjectService.findAll();
    }

}
