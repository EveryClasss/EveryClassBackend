package com.everyclass.dto;
import lombok.Builder; import lombok.Data; import java.time.LocalDateTime;
@Data @Builder
public class ReviewDto { private Long id; private Long teacherId; private Long userId; private String userName; private int rating; private String comment; private LocalDateTime createdAt; }