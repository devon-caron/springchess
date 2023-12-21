package com.dpc.springchess.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dpc.springchess.dtos.BoardResponseDto;
import com.dpc.springchess.dtos.CredentialsDto;
import com.dpc.springchess.dtos.UserRequestDto;
import com.dpc.springchess.dtos.UserResponseDto;
import com.dpc.springchess.entities.Board;
import com.dpc.springchess.entities.Credentials;
import com.dpc.springchess.entities.Profile;
import com.dpc.springchess.entities.User;
import com.dpc.springchess.mappers.BoardMapper;
import com.dpc.springchess.mappers.UserMapper;
import com.dpc.springchess.repositories.UserRepository;
import com.dpc.springchess.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	private final BoardMapper boardMapper;
	
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

	/**
	 *
	 */
	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		Credentials credentials = userRequestDto.getCredentials();
		Profile profile = userRequestDto.getProfile();
		
		User myUser = new User();
		Credentials myCreds = new Credentials();
		Profile myProfile = new Profile();
		
		myCreds.setUsername(credentials.getUsername());
		myCreds.setPassword(credentials.getPassword());
		myProfile.setName(profile.getName());
		myProfile.setTitle(profile.getTitle());
		myProfile.setRating(profile.getRating());
		
		
		myUser.setCredentials(myCreds);
		myUser.setProfile(myProfile);
		
		return userMapper.entityToDto(userRepository.saveAndFlush(myUser));
	}

	@Override
	public List<UserResponseDto> getFollowing(String username) {
		Optional<User> optUser = userRepository.findByCredentials_Username(username);
		Credentials myCreds = new Credentials();

		if (optUser.isEmpty()) {
			// TODO: not found
			return null;
		}
		
		User myUser = optUser.get();
		
		
		return userMapper.entitiesToDtos(myUser.getFollowing());
		
	}

	@Override
	public UserResponseDto followUser(String username, CredentialsDto credentialsDto) {
		Optional<User> myOptUser = userRepository.findByCredentials_Username(credentialsDto.getUsername());
		Optional<User> folOptUser = userRepository.findByCredentials_Username(username);
		Credentials myCreds = new Credentials();

		myCreds.setUsername(credentialsDto.getUsername());
		myCreds.setPassword(credentialsDto.getPassword());

		if (myOptUser.isEmpty()) {
			System.out.println(credentialsDto.getUsername() + "myUser not found");
			// TODO: not found
			return null;
		}
		
		if (folOptUser.isEmpty()) {
			System.out.println("folUser not found");
			return null;
		}
		
		User myUser = myOptUser.get();
		User folUser = folOptUser.get();
		
		if (!authenticate(myCreds, myUser.getCredentials())) {
			System.out.println("Invalid Credentials");
			System.out.println("Mycreds, user: " + myCreds.getUsername() + " pass: " + myCreds.getPassword());
			System.out.println("usercreds, user: " + myUser.getCredentials().getUsername() + " pass: " + myUser.getCredentials().getPassword());
			// TODO: Auth error
			return null;
		}
		
		List<User> myFollowingList = myUser.getFollowing();
		List<User> theirFollowerList = folUser.getFollowers();
		
		if (myFollowingList.contains(folUser)) {
			// TODO: bad request since myUser already follows folUser
			System.out.println("Already following");
			
			return null;
		}
		
		myFollowingList.add(folUser);
		theirFollowerList.add(myUser);
		
		myUser.setFollowing(myFollowingList);
		folUser.setFollowers(theirFollowerList);
		
		userRepository.saveAndFlush(myUser);
		
		for (User user : myFollowingList) {
			System.out.println(user.getCredentials().getUsername());
		}
		
		return userMapper.entityToDto(userRepository.saveAndFlush(folUser));
	}


	@Override
	public UserResponseDto unfollowUser(String username, CredentialsDto credentialsDto) {
		Optional<User> myOptUser = userRepository.findByCredentials_Username(credentialsDto.getUsername());
		Optional<User> folOptUser = userRepository.findByCredentials_Username(username);
		Credentials myCreds = new Credentials();

		myCreds.setUsername(credentialsDto.getUsername());
		myCreds.setPassword(credentialsDto.getPassword());

		if (myOptUser.isEmpty()) {
			System.out.println(credentialsDto.getUsername() + "myUser not found");
			// TODO: not found
			return null;
		}
		
		if (folOptUser.isEmpty()) {
			System.out.println("folUser not found");
			return null;
		}
		
		User myUser = myOptUser.get();
		User folUser = folOptUser.get();
		
		if (!authenticate(myCreds, myUser.getCredentials())) {
			System.out.println("Invalid Credentials");
			System.out.println("Mycreds, user: " + myCreds.getUsername() + " pass: " + myCreds.getPassword());
			System.out.println("usercreds, user: " + myUser.getCredentials().getUsername() + " pass: " + myUser.getCredentials().getPassword());
			// TODO: Auth error
			return null;
		}
		
		List<User> myFollowingList = myUser.getFollowing();
		List<User> theirFollowerList = folUser.getFollowers();
		
		if (!myFollowingList.contains(folUser) || !theirFollowerList.contains(myUser)) {
			// TODO: bad request since myUser doesn't follow folUser
			System.out.println("not following!");
			
			return null;
		}
		
		myFollowingList.remove(folUser);
		theirFollowerList.remove(myUser);
		
		myUser.setFollowing(myFollowingList);
		folUser.setFollowers(theirFollowerList);
		
		userRepository.saveAndFlush(myUser);
		
		return userMapper.entityToDto(userRepository.saveAndFlush(folUser));
		
	}

	@Override
	public List<BoardResponseDto> getSavedGames(String username) {
		Optional<User> optUser = userRepository.findByCredentials_Username(username);
		
		if (optUser.isEmpty()) {
			// TODO: not found
			return null;
		}
		
		User myUser = optUser.get();
		
		List<Board> games = myUser.getGames();
		
		return boardMapper.entitiesToDtos(myUser.getGames());
	}


	
	private boolean authenticate(Credentials toCheck, Credentials reference) {
		return (toCheck.getUsername().trim().equals(reference.getUsername().trim()) && toCheck.getPassword().trim().equals(reference.getPassword().trim()));
	}

}
