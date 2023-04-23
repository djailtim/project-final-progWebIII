package com.example.apiusers.dto;

import com.example.apiusers.dto.request.UserRequest;
import com.example.apiusers.dto.request.UserRequestUpdate;
import com.example.apiusers.dto.response.UserResponse;
import com.example.apiusers.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class UserMapper {
    public UserResponse mapToUserResponse (User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public User mapUserWithUserRequestData(UserRequest userRequest) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .username(userRequest.username())
                .password(userRequest.password())
                .isAdmin(false)
                .isActive(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public User mapUserWithUserRequestUpdateData(User user, UserRequestUpdate userRequestUpdate) {
        return User.builder()
                .id(user.getId())
                .firstName(userRequestUpdate.firstName() == null ? user.getFirstName() : userRequestUpdate.firstName())
                .lastName(userRequestUpdate.lastName() == null ? user.getLastName() : userRequestUpdate.lastName())
                .username(user.getUsername())
                .password(userRequestUpdate.password() == null ? user.getPassword() : userRequestUpdate.password())
                .isAdmin(user.isAdmin())
                .isActive(user.isActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(Instant.now())
                .build();
    }
}
