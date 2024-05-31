package com.example.apinewsservice.mapper;

import com.example.apinewsservice.model.User;
import com.example.apinewsservice.web.model.UserCreateRequest;
import com.example.apinewsservice.web.model.UserResponseList;
import com.example.apinewsservice.web.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
public interface UserMapper {

    User requestToUser(UserCreateRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UserCreateRequest request);

    UserResponse userToResponse(User user);

    default UserResponseList userListToUserResponseList(List<User> users) {
        UserResponseList response = new UserResponseList();

        response.setUsers(users.stream()
                .map(this::userToResponse).collect(Collectors.toList()));
        return response;
    }
}