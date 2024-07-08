package com.example.newsservice.web.controller;

import com.example.newsservice.aop.Secured;
import com.example.newsservice.mapper.UserMapper;
import com.example.newsservice.model.RoleType;
import com.example.newsservice.model.User;
import com.example.newsservice.service.UserService;
import com.example.newsservice.web.model.UpsertUserRequest;
import com.example.newsservice.web.model.filter.UserFilter;
import com.example.newsservice.web.model.UserListResponse;
import com.example.newsservice.web.model.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserListResponse> findAll(@Valid UserFilter filter, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                userMapper.userListToResponseList(userService.findAll(filter)));
    }

    @GetMapping("/{id}")
    @Secured(roles = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<UserResponse> findById(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

    @PostMapping()
    public ResponseEntity<UserResponse> create(@RequestBody UpsertUserRequest request, @RequestParam RoleType roleType) {
        User newUser = userService.save(userMapper.requestToUser(request), roleType);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    @Secured(roles = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId, @RequestBody UpsertUserRequest request) {
        User updatedUser = userService.update(userMapper.requestToUser(userId,request));
        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @Secured(roles = {"ROLE_ADMIN", "ROLE_MODERATOR"})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
