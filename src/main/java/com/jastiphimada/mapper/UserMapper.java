package com.jastiphimada.mapper;

import com.jastiphimada.dto.UserDto;
import com.jastiphimada.entity.User;

public class UserMapper {

    public static User mapToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .role(userDto.getRole())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }
}
