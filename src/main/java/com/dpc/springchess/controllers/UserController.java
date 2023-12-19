package com.dpc.springchess.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpc.springchess.dtos.CredentialsDto;
import com.dpc.springchess.dtos.UserResponseDto;
import com.dpc.springchess.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping
	public UserResponseDto createUser(@RequestBody CredentialsDto credentialsDto) {
		return userService.createUser(credentialsDto);
	}
	
	@GetMapping("/@{username}")
	public UserResponseDto getUser(@PathVariable(value="username") String username) {
		return userService.getUser(username);
	}
	
	@GetMapping("/follow")
	public List<UserResponseDto> getFollowers(@RequestBody CredentialsDto credentialsDto) {
		return userService.getFollowers(credentialsDto);
	}
	
	@PostMapping("/follow/@{username}")
	public UserResponseDto followUser(@PathVariable(value="username") String username, @RequestBody CredentialsDto credentialsDto) {
		return userService.followUser(username, credentialsDto);
	}
}
