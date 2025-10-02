package com.everyclass.dto;
import lombok.Builder; import lombok.Data;
@Data @Builder
public class AttachmentDto { private Long id; private Long messageId; private String fileUrl; private String fileType; }