package com.everyclass.controller;

import com.everyclass.dto.FavoriteDto;
import com.everyclass.service.FavoriteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<FavoriteDto> add(@RequestBody AddFav req){
        return ResponseEntity.ok(favoriteService.add(req.userId, req.teacherId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteDto>> list(@PathVariable Long userId){
        return ResponseEntity.ok(favoriteService.list(userId));
    }

    @Data
    public static class AddFav {
        public Long userId;
        public Long teacherId;
    }
}
