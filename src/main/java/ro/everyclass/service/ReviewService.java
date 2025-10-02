package com.everyclass.service;

import com.everyclass.dto.ReviewDto;
import com.everyclass.entity.Review;
import com.everyclass.entity.TeacherProfile;
import com.everyclass.entity.User;
import com.everyclass.exception.ResourceNotFoundException;
import com.everyclass.repository.ReviewRepository;
import com.everyclass.repository.TeacherProfileRepository;
import com.everyclass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepo;
    private final TeacherProfileRepository teacherRepo;
    private final UserRepository userRepo;

    public ReviewDto addReview(Long teacherId, Long userId, int rating, String comment){
        TeacherProfile t = teacherRepo.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        User u = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Review r = Review.builder().teacher(t).user(u).rating(rating).comment(comment).build();
        r = reviewRepo.save(r);
        List<Review> all = reviewRepo.findByTeacher(t);
        double avg = all.stream().mapToInt(Review::getRating).average().orElse(0.0);
        t.setRatingAvg(Math.round(avg*100.0)/100.0);
        teacherRepo.save(t);
        return ReviewDto.builder()
                .id(r.getId()).teacherId(t.getId()).userId(u.getId()).userName(u.getName())
                .rating(r.getRating()).comment(r.getComment()).createdAt(r.getCreatedAt())
                .build();
    }

    public List<ReviewDto> listForTeacher(Long teacherId){
        TeacherProfile t = teacherRepo.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        return reviewRepo.findByTeacher(t).stream().map(r -> ReviewDto.builder()
                .id(r.getId()).teacherId(t.getId()).userId(r.getUser().getId()).userName(r.getUser().getName())
                .rating(r.getRating()).comment(r.getComment()).createdAt(r.getCreatedAt())
                .build()).toList();
    }
}
