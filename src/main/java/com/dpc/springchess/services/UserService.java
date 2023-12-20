package com.dpc.springchess.services;

import java.util.List;

import com.dpc.springchess.dtos.CredentialsDto;
import com.dpc.springchess.dtos.UserRequestDto;
import com.dpc.springchess.dtos.UserResponseDto;

public interface UserService {

	UserResponseDto getUser(String username);

	UserResponseDto createUser(UserRequestDto userRequestDto);

	List<UserResponseDto> getFollowers(CredentialsDto credentialsDto);

	UserResponseDto followUser(String username, CredentialsDto credentialsDto);

}
