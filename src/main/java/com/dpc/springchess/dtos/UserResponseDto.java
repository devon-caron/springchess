package com.dpc.springchess.dtos;


import com.dpc.springchess.entities.Profile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
	private Profile profile;
}
