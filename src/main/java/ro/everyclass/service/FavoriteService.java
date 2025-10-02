package com.everyclass.service;

import com.everyclass.dto.FavoriteDto;
import com.everyclass.entity.Favorite;
import com.everyclass.entity.TeacherProfile;
import com.everyclass.entity.User;
import com.everyclass.exception.ResourceNotFoundException;
import com.everyclass.repository.FavoriteRepository;
import com.everyclass.repository.TeacherProfileRepository;
import com.everyclass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepo;
    private final UserRepository userRepo;
    private final TeacherProfileRepository teacherRepo;

    public FavoriteDto add(Long userId, Long teacherId){
        User u = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        TeacherProfile t = teacherRepo.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Teacher not found"));
        if (favoriteRepo.existsByUserAndTeacher(u,t)) {
            throw new RuntimeException("Already in favorites");
        }
        Favorite f = favoriteRepo.save(Favorite.builder().user(u).teacher(t).build());
        return FavoriteDto.builder().id(f.getId()).userId(u.getId()).teacherId(t.getId()).teacherName(t.getUser().getName()).build();
    }

    public List<FavoriteDto> list(Long userId){
        User u = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return favoriteRepo.findByUser(u).stream().map(f -> FavoriteDto.builder()
                .id(f.getId()).userId(u.getId()).teacherId(f.getTeacher().getId()).teacherName(f.getTeacher().getUser().getName())
                .build()).toList();
    }
}
