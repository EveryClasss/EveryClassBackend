package com.everyclass.controller;

import com.everyclass.dto.TeacherProfileDto;
import com.everyclass.service.TeacherService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<TeacherProfileDto>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String county,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ){
        return ResponseEntity.ok(teacherService.search(city, county, minPrice, maxPrice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherProfileDto> get(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getOne(id));
    }

    @PostMapping
    public ResponseEntity<?> upsert(@RequestBody UpsertTeacher req){
        return ResponseEntity.ok(teacherService.createOrUpdate(req.userId, req.city, req.county, req.pricePerHour, req.description, req.photoUrl, req.subjects));
    }

    @Data
    public static class UpsertTeacher {
        public Long userId;
        public String city;
        public String county;
        public Double pricePerHour;
        public String description;
        public String photoUrl;
        public List<String> subjects;
    }
}
