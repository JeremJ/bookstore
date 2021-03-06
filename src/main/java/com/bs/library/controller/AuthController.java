package com.bs.library.controller;

import com.bs.library.dto.LoginDTO;
import com.bs.library.dto.UserDTO;
import com.bs.library.mapper.UserMapper;
import com.bs.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.authenticateUser(loginDTO.getUsername(),loginDTO.getPassword()));
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
        userService.registerUser(userMapper.toUser(userDTO));
        return ResponseEntity.ok().body("User registered successfully!");
    }
}
