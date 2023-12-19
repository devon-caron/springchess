package com.dpc.springchess.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.dpc.springchess.dtos.UserRequestDto;
import com.dpc.springchess.dtos.UserResponseDto;
import com.dpc.springchess.entities.User;

@Mapper(componentModel="spring")
public interface UserMapper {

	User dtoToEntity(UserRequestDto userRequestDto);

	UserResponseDto entityToDto(User user);

	List<UserResponseDto> entitiesToDtos(List<User> users);

}
