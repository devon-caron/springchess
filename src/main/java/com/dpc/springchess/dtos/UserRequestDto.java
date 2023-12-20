package com.dpc.springchess.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
	private CredentialsDto credentialsDto;
	private ProfileDto profileDto;
}
