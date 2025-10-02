package com.everyclass.dto;
import lombok.Builder; import lombok.Data; import java.time.LocalDateTime;
@Data @Builder
public class ReportDto { private Long id; private Long reportedUserId; private Long reporterId; private String reporterName; private String reason; private LocalDateTime createdAt; }