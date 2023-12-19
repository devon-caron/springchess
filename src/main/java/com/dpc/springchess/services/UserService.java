package com.dpc.springchess.services;

import com.dpc.springchess.dtos.UserResponseDto;

public interface UserService {

	UserResponseDto getUser(String username);

}
