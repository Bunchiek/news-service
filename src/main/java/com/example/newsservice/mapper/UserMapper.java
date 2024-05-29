package com.example.newsservice.mapper;

import com.example.newsservice.model.User;
import com.example.newsservice.web.model.UpsertUserRequest;
import com.example.newsservice.web.model.UserListResponse;
import com.example.newsservice.web.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UpsertUserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UpsertUserRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse userListToResponseList(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(users.stream()
                .map(this::userToResponse).toList());
        return response;
    }

}
