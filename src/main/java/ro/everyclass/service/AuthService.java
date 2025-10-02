package com.everyclass.service;

import com.everyclass.dto.AuthResponse;
import com.everyclass.dto.LoginRequest;
import com.everyclass.dto.RegisterRequest;
import com.everyclass.entity.Role;
import com.everyclass.entity.User;
import com.everyclass.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest req){
        Role role = Role.valueOf(req.getRole().toUpperCase());
        User u = userService.createUser(req.getName(), req.getEmail(), req.getPassword(), role);
        String token = jwtUtil.generateToken(u.getEmail(), new HashMap<>(){{ put("role", u.getRole().name()); put("uid", u.getId()); }});
        return new AuthResponse(token, u.getId(), u.getRole().name());
    }

    public AuthResponse login(LoginRequest req){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User u = userService.findByEmailOrThrow(req.getEmail());
        String token = jwtUtil.generateToken(u.getEmail(), new HashMap<>(){{ put("role", u.getRole().name()); put("uid", u.getId()); }});
        return new AuthResponse(token, u.getId(), u.getRole().name());
    }
}
