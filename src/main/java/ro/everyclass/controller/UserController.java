package com.everyclass.controller;

import com.everyclass.dto.UserDto;
import com.everyclass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserDto>> all(){
        return ResponseEntity.ok(userRepository.findAll().stream().map(u -> UserDto.builder()
                .id(u.getId()).name(u.getName()).email(u.getEmail()).role(u.getRole().name()).profilePictureUrl(u.getProfilePictureUrl()).build()
        ).toList());
    }
}
