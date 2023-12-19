package com.dpc.springchess.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dpc.springchess.dtos.UserResponseDto;
import com.dpc.springchess.entities.User;
import com.dpc.springchess.mappers.UserMapper;
import com.dpc.springchess.repositories.UserRepository;
import com.dpc.springchess.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	@Override
	public UserResponseDto getUser(String username) {
		
		Optional<User> optUser = userRepository.findByCredentialsUsername(username);
		if (optUser.isEmpty()) {
			// TODO: Throw exception
			return null;
		}
		
		User myUser = optUser.get();
		
		return userMapper.dtoToEntity(myUser);
	}

}
