package com.dpc.springchess.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dpc.springchess.dtos.UserResponseDto;
import com.dpc.springchess.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/@{username}")
	public UserResponseDto getUser(@PathVariable(value="username") String username) {
		return userService.getUser(username);
	}
}
