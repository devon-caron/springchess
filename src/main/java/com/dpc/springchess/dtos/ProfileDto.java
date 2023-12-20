package com.dpc.springchess.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileDto {
	private String name;
	private String title;
	private String rating;
}
