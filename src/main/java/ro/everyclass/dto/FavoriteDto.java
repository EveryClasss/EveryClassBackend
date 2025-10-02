package com.everyclass.dto;
import lombok.Builder; import lombok.Data;
@Data @Builder
public class FavoriteDto { private Long id; private Long userId; private Long teacherId; private String teacherName; }