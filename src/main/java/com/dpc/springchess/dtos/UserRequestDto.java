package com.dpc.springchess.dtos;

import com.dpc.springchess.entities.Credentials;
import com.dpc.springchess.entities.Profile;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Getter
@Setter
public class UserRequestDto {
	private Credentials credentials;
	private Profile profile;
}
