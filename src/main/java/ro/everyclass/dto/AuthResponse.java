package com.everyclass.dto;
import lombok.AllArgsConstructor; import lombok.Data;
@Data @AllArgsConstructor
public class AuthResponse { private String token; private Long userId; private String role; }