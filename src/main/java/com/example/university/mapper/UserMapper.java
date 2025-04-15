package com.example.university.mapper;

import com.example.university.model.dto.FullUserDto;
import com.example.university.model.dto.UserDto;
import com.example.university.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    FullUserDto toFullDto(User user);
}
