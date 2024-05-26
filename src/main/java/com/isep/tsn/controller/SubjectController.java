package com.isep.tsn.controller;

import com.isep.tsn.dal.model.dto.SubjectDto;
import com.isep.tsn.service.SubjectService;
import com.isep.tsn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getSubjects() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @PostMapping
    public ResponseEntity<SubjectDto> addSubject(@RequestBody SubjectDto subjectDto) {
        return ResponseEntity.ok(subjectService.addSubject(subjectDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SubjectDto>> getUserSubjects(@PathVariable String userId) {
        var user = userService.getUser(userId);
        return ResponseEntity.ok(subjectService.getUserSubjects(user));
    }

}
