package com.everyclass.dto;
import lombok.Builder; import lombok.Data;
@Data @Builder
public class UserDto { private Long id; private String name; private String email; private String role; private String profilePictureUrl; }