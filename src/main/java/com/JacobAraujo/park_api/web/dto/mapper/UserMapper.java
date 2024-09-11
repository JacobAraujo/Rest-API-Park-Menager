package com.JacobAraujo.park_api.web.dto.mapper;

import com.JacobAraujo.park_api.entity.User;
import com.JacobAraujo.park_api.web.dto.UserCreateDto;
import com.JacobAraujo.park_api.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserCreateDto createDto){
        return new ModelMapper().map(createDto, User.class);
    }

        public static UserResponseDto toDto(User user) {
            String role = user.getRole().name().substring("ROLE_".length());
            ModelMapper mapperMain = new ModelMapper();
            TypeMap<User, UserResponseDto> propertyMapper = mapperMain.createTypeMap(User.class, UserResponseDto.class);
            propertyMapper.addMappings(
                    mapper -> mapper.map(src -> role, UserResponseDto::setRole)
            );
            return mapperMain.map(user, UserResponseDto.class);
        }

    public static List<UserResponseDto> toListDto(List<User> users){
        return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
