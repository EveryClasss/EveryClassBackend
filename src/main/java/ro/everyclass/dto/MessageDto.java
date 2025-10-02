package com.everyclass.dto;
import lombok.Builder; import lombok.Data; import java.time.LocalDateTime;
@Data @Builder
public class MessageDto { private Long id; private Long senderId; private Long receiverId; private String senderName; private String receiverName; private String content; private LocalDateTime createdAt; }