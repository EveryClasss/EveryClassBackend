package com.everyclass.controller;

import com.everyclass.dto.ReviewDto;
import com.everyclass.service.ReviewService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDto> add(@RequestBody CreateReview req){
        return ResponseEntity.ok(reviewService.addReview(req.teacherId, req.userId, req.rating, req.comment));
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<ReviewDto>> list(@PathVariable Long teacherId){
        return ResponseEntity.ok(reviewService.listForTeacher(teacherId));
    }

    @Data
    public static class CreateReview {
        public Long teacherId;
        public Long userId;
        public int rating;
        public String comment;
    }
}
