package com.isep.tsn.controller;

import com.isep.tsn.dal.model.dto.SubjectDto;
import com.isep.tsn.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getSubjects() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @PostMapping
    public ResponseEntity<SubjectDto> addSubject(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.addSubject(subjectDto));
    }

    @GetMapping("/posts/{userId}")
    public ResponseEntity<List<SubjectDto>> getUserSubjects(@PathVariable String userId) {
        return ResponseEntity.ok(subjectService.getUserSubjects(userId));
    }

}
