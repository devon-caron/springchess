package com.dpc.springchess.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dpc.springchess.dtos.CredentialsDto;
import com.dpc.springchess.dtos.UserResponseDto;
import com.dpc.springchess.entities.Credentials;
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
		
		Optional<User> optUser = userRepository.findByCredentials_Username(username);
		if (optUser.isEmpty()) {
			// TODO: Throw exception
			return null;
		}
		
		User myUser = optUser.get();
		
		return userMapper.entityToDto(userRepository.saveAndFlush(myUser));
	}

	@Override
	public UserResponseDto createUser(CredentialsDto credentialsDto) {
		System.out.println("here");
		User myUser = new User();
		User test = new User();
		Credentials myCreds = new Credentials();
		System.out.println("here");
		
		myCreds.setUsername(credentialsDto.getUsername());
		myCreds.setPassword(credentialsDto.getPassword());
		
		
		myUser.setCredentials(myCreds);
		System.out.println(myUser.getCredentials().getUsername());

		System.out.println("here");
		test = userRepository.saveAndFlush(myUser);
		
		System.out.println(test.getId());
		
		return userMapper.entityToDto(userRepository.saveAndFlush(myUser));
	}

	@Override
	public List<UserResponseDto> getFollowers(CredentialsDto credentialsDto) {
		Optional<User> optUser = userRepository.findByCredentials_Username(credentialsDto.getUsername());
		Credentials myCreds = new Credentials();

		myCreds.setUsername(credentialsDto.getUsername());
		myCreds.setPassword(credentialsDto.getPassword());

		if (optUser.isEmpty()) {
			// TODO: not found
			return null;
		}
		
		User myUser = optUser.get();
		
		if (!authenticate(myCreds, myUser.getCredentials())) {
			// TODO: Auth error
			return null;
		}
		
		return userMapper.entitiesToDtos(myUser.getFollowers());
		
	}

	@Override
	public UserResponseDto followUser(String username, CredentialsDto credentialsDto) {
		Optional<User> myOptUser = userRepository.findByCredentials_Username(credentialsDto.getUsername());
		Optional<User> folOptUser = userRepository.findByCredentials_Username(username);
		Credentials myCreds = new Credentials();

		myCreds.setUsername(credentialsDto.getUsername());
		myCreds.setPassword(credentialsDto.getPassword());

		if (myOptUser.isEmpty() || folOptUser.isEmpty()) {
			// TODO: not found
			return null;
		}
		
		User myUser = myOptUser.get();
		User folUser = folOptUser.get();
		
		if (!authenticate(myCreds, myUser.getCredentials())) {
			// TODO: Auth error
			return null;
		}
		
		List<User> myFollowingList = myUser.getFollowing();
		List<User> theirFollowerList = folUser.getFollowers();
		
		if (myFollowingList.contains(folUser)) {
			// TODO: bad request since myUser already follows folUser
			
			return null;
		}
		
		myFollowingList.add(folUser);
		myUser.setFollowing(myFollowingList);
		
		theirFollowerList.add(myUser);
		folUser.setFollowers(theirFollowerList);
		
		userRepository.saveAndFlush(myUser);
		
		return userMapper.entityToDto(userRepository.saveAndFlush(folUser));
	}

	private boolean authenticate(Credentials toCheck, Credentials reference) {
		return toCheck.equals(reference);
	}

}
