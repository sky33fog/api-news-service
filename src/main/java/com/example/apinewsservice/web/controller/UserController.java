package com.example.apinewsservice.web.controller;

import com.example.apinewsservice.aop.CheckingUser;
import com.example.apinewsservice.mapper.UserMapper;
import com.example.apinewsservice.model.Role;
import com.example.apinewsservice.model.RoleType;
import com.example.apinewsservice.model.User;
import com.example.apinewsservice.service.UserService;
import com.example.apinewsservice.web.model.UserCreateRequest;
import com.example.apinewsservice.web.model.UserFilter;
import com.example.apinewsservice.web.model.UserResponseList;
import com.example.apinewsservice.web.model.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseList> findAll(@Valid UserFilter filter) {
        return ResponseEntity.ok(
                userMapper.userListToUserResponseList(userService.findAll(filter)));
    }

    @GetMapping("/{id}")
    @CheckingUser
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.userToResponse(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserCreateRequest request,
                                               @RequestParam RoleType roleType) {
        User newUser = userService.save(userMapper.requestToUser(request), Role.from(roleType));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @CheckingUser
    public ResponseEntity<UserResponse> update(@PathVariable Long id,
                                               @RequestBody @Valid UserCreateRequest request) {
        User updatedUser = userService.update(userMapper.requestToUser(id, request));

        return ResponseEntity.ok(userMapper.userToResponse(updatedUser));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @CheckingUser
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
