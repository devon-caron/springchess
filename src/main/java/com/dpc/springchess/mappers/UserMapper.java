package com.dpc.springchess.mappers;

import org.mapstruct.Mapper;

import com.dpc.springchess.dtos.UserResponseDto;
import com.dpc.springchess.entities.User;

@Mapper(componentModel="spring")
public interface UserMapper {

	UserResponseDto dtoToEntity(User myUser);

}
