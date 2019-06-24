package com.bs.library.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity signUp(@RequestBody UserDTO userDTO) {
        userService.save(userMapper.toUser(userDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body("Record inserted Successfully !");
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(userMapper.toUserDTOs(users));
    }
}
